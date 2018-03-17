package inc.troll.mnemescan.services.network;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inc.troll.mnemescan.config.DatabaseConfiguration;
import inc.troll.mnemescan.data.NetworkCoordinates;
import inc.troll.mnemescan.data.WirelessNetwork;
import inc.troll.mnemescan.data.repositories.NetworkCoordinatesRepository;
import inc.troll.mnemescan.data.repositories.WirelessNetworkRepository;
import inc.troll.mnemescan.services.network.dto.CoordinatesDto;
import inc.troll.mnemescan.services.network.dto.NetworkDto;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScanDataTransferService {

	private DatabaseConfiguration database;
	private WirelessNetworkRepository wirelessNetworkRepository;
	private NetworkCoordinatesRepository networkCoordinatesRepository;
	private Gson gson;
	private OkHttpClient okHttpClient;

//	public ScanDataTransferService(){}
	public ScanDataTransferService(Context context) {
		database = Room.databaseBuilder(context, DatabaseConfiguration.class, "mneme_scan").build();
		wirelessNetworkRepository = database.getWirelessNetworkRepository();
		networkCoordinatesRepository = database.getNetworkCoordinatesRepository();
		gson = new GsonBuilder().serializeNulls().create();
		okHttpClient = new OkHttpClient();
	}

	// TODO improve threading here
	public void transferScanData() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... voids) {
				try{
					transferNewNetworks();
					transferUpdatedNetworks();
					transferNewCoordinates();
				} catch(IOException ioE) {
					System.out.println("ERROR: could not transfer data");
					ioE.printStackTrace();
				}

				return null;
			}
		}.execute();
	}

	// TODO make base url, port and endpoint configurable
	private void transferNewNetworks() throws IOException {
		List<WirelessNetwork> scannedNetworks = wirelessNetworkRepository.findAllNew();
		List<Long> ids = new ArrayList<Long>();
		List<NetworkDto> networkDtoList = new ArrayList<NetworkDto>();

		for(WirelessNetwork network : scannedNetworks) {
			networkDtoList.add(new NetworkDto(network));
			ids.add(network.getId());
		}

		Response response = sendJson("POST","http://pi.home.lan:4567/networks", networkDtoList);

		// change status of success full transferred entries
		if(response.isSuccessful()) {
			wirelessNetworkRepository.markAsTransferred(ids);
		} else {
			// TODO improve error behavior
			System.out.println("ERROR: could not transfer new networks");
			System.out.println(response.message());
		}
	}

	// TODO make base url, port and endpoint configurable
	private void transferUpdatedNetworks() throws IOException {
		List<WirelessNetwork> scannedNetworks = wirelessNetworkRepository.findAllUpdated();
		List<Long> ids = new ArrayList<Long>();
		List<NetworkDto> networkDtoList = new ArrayList<NetworkDto>();

		for(WirelessNetwork network : scannedNetworks) {
			networkDtoList.add(new NetworkDto(network));
			ids.add(network.getId());
		}

		Response response = sendJson("PUT","http://pi.home.lan:4567/networks", networkDtoList);

		// change status of success full transferred entries
		if(response.isSuccessful()) {
			wirelessNetworkRepository.markAsTransferred(ids);
		} else {
			// TODO improve error behavior
			System.out.println("ERROR: could not transfer new networks");
			System.out.println(response.message());
		}
	}

	// TODO make base url, port and endpoint configurable
	private void transferNewCoordinates() throws IOException {
		List<NetworkCoordinates> coordinatesList = networkCoordinatesRepository.findAllNew();
		List<Long> ids = new ArrayList<Long>();
		List<CoordinatesDto> coordinatesDtoList = new ArrayList<CoordinatesDto>();

		for(NetworkCoordinates coordinates : coordinatesList) {
			coordinatesDtoList.add(new CoordinatesDto(coordinates));
			ids.add(coordinates.getId());
		}

		Response response = sendJson("POST","http://pi.home.lan:4567/coordinates", coordinatesDtoList);

		// change status of success full transferred entries
		if(response.isSuccessful()) {
			networkCoordinatesRepository.markAsTransferred(ids);
		} else {
			// TODO improve error behavior
			System.out.println("ERROR: could not transfer new coorinates");
			System.out.println(response.message());
		}
	}

	private Response sendJson(String method, String url, Object data) throws IOException {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(JSON, gson.toJson(data));
		Request request = new Request.Builder()
				.url(url)
				.method(method, body)
				.build();

		return okHttpClient.newCall(request).execute();
	}
}
