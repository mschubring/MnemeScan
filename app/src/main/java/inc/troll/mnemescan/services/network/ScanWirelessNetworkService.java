package inc.troll.mnemescan.services.network;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import inc.troll.mnemescan.config.DatabaseConfiguration;
import inc.troll.mnemescan.data.NetworkCoordinates;
import inc.troll.mnemescan.data.WirelessNetwork;
import inc.troll.mnemescan.data.repositories.NetworkCoordinatesRepository;
import inc.troll.mnemescan.data.repositories.WirelessNetworkRepository;
import inc.troll.mnemescan.util.Converter;

public class ScanWirelessNetworkService {

	private WifiManager wifiManager;
	private LocationManager locationManager;
	private DatabaseConfiguration database;
	private Context context;
	private TextView displayTextView;
	private boolean enableScanning = false;
	private WirelessNetworkRepository wirelessNetworkRepository;
	private NetworkCoordinatesRepository networkCoordinatesRepository;
	private float currentLatitude = 0;
	private float currentLongitude = 0;

	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			currentLatitude = Converter.roundCoordinates(location.getLatitude(),5);
			currentLongitude = Converter.roundCoordinates(location.getLongitude(),5);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
	};

	private final BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent intent) {
			if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
				List<ScanResult> scanResults = wifiManager.getScanResults();
				displayNetworkData(scanResults);
				processScanResults(scanResults);
			}
		}
	};

	public ScanWirelessNetworkService(Context context, TextView displayTextView) {
		this.context = context;
		this.displayTextView = displayTextView;

		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		database = Room.databaseBuilder(context, DatabaseConfiguration.class, "mneme_scan").build();
		wirelessNetworkRepository = database.getWirelessNetworkRepository();
		networkCoordinatesRepository = database.getNetworkCoordinatesRepository();

		context.registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
	}

	// TODO improve threading here
	private void processScanResults(final List<ScanResult> scanResults) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... voids) {
				List<WirelessNetwork> networkList = new ArrayList<WirelessNetwork>();

				for(ScanResult scanResult : scanResults) {
					// TODO refactor and use just one query (findByMacAddressList) instead of many
					WirelessNetwork network = wirelessNetworkRepository.findByMacAddress(scanResult.BSSID);

					// network already known?
					if(network != null) {
						// known  -> update?
						updateKnownNetwork(network, scanResult);
						updateKnownNetworkCoordinatesList(scanResult, currentLatitude, currentLongitude);
					} else {
						// new -> add netward and coordinates
						wirelessNetworkRepository.insert(new WirelessNetwork(scanResult));
						networkCoordinatesRepository.insert(new NetworkCoordinates(scanResult.BSSID, scanResult.level, currentLatitude, currentLongitude));
					}
				}

				if(enableScanning) {
					wifiManager.startScan();
				}

				return null;
			}
		}.execute();
	}

	public void startScan() {
		enableScanning = true;
		wifiManager.startScan();
	}

	private WirelessNetwork updateKnownNetwork(WirelessNetwork network, ScanResult scanResult) {
		String newHash = WirelessNetwork.buildHash(scanResult);
		String knownHash = network.getDataHash();

		// hash changed?
		if(!newHash.equals(knownHash)) {
			// yes -> update db entry
			network.update(scanResult);
			wirelessNetworkRepository.update(network);
		}

		return network;
	}

	private void updateKnownNetworkCoordinatesList(ScanResult scanResult, float currentLatitude, float currentLongitude) {
		// note: I decided to not include signal strength into insert decision
		if(networkCoordinatesRepository.findByMacAddressAndCoordinates(scanResult.BSSID, currentLatitude, currentLongitude) == null) {
			networkCoordinatesRepository.insert(new NetworkCoordinates(scanResult.BSSID, scanResult.level, currentLatitude, currentLongitude));
		}
	}

	private void displayNetworkData(List<ScanResult> scanResults) {
		StringBuilder stringBuilder = new StringBuilder();

		for(ScanResult scanResult : scanResults) {
			stringBuilder.append(scanResult.SSID)
					.append(" ")
					.append(scanResult.capabilities)
					.append("\n");
		}

		stringBuilder.append("POSITION")
				.append("\tLat: ").append(currentLatitude)
				.append("\tLng: ").append(currentLongitude)
				.append("\n");
		displayTextView.setText(stringBuilder.toString());
	}

	public void stopScan(){
		enableScanning = false;
	}

	public boolean isScanning(){
		return enableScanning;
	}
}
