package inc.troll.mnemescan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.net.wifi.WifiManager;

import java.util.Calendar;

import inc.troll.mnemescan.data.BaseEntity;
import inc.troll.mnemescan.util.Converter;

@Entity(tableName = "network_coordinates",
		indices = {@Index(name = "n_c_mac_address_idx", value = "mac_address")},
		foreignKeys = @ForeignKey(entity = WirelessNetwork.class, parentColumns = "mac_address", childColumns = "mac_address"))
public class NetworkCoordinates extends BaseEntity{

	@ColumnInfo(name = "mac_address")
	private String macAddress;

	@ColumnInfo(name = "signal_strength")
	private int signalStrength;

	@ColumnInfo(name = "latitude")
	private float latitude;

	@ColumnInfo(name = "longitude")
	private float longitude;

	@ColumnInfo(name = "is_new")
	private boolean newEntry = true;

	public NetworkCoordinates(String macAddress,int signalStrength, float latitude, float longitude) {
		this.macAddress = macAddress;
		this.signalStrength = WifiManager.calculateSignalLevel(signalStrength, 100);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public boolean isNewEntry() {
		return newEntry;
	}
	public void setNewEntry(boolean newEntry) {
		this.newEntry = newEntry;
	}
}
