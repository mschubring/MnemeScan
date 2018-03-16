package inc.troll.mnemescan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Query;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import inc.troll.mnemescan.util.Converter;

@Entity(tableName = "wireless_networks",
		indices = {@Index(name = "w_n_mac_address_idx", value = "mac_address", unique = true)})
public class WirelessNetwork extends BaseEntity {

	@ColumnInfo(name = "mac_address")
	private String macAddress;

	@ColumnInfo(name = "ssid")
	private String ssid;

	@ColumnInfo(name = "frequency")
	private int frequency;

	@ColumnInfo(name = "capabilities")
	private String capabilities;

	@ColumnInfo(name = "data_hash")
	private String dataHash;

	@ColumnInfo(name = "update_count")
	private int updateCount = 0;

	@ColumnInfo(name = "is_new")
	private boolean newData = true;

	@ColumnInfo(name = "is_updated")
	private boolean updatedData = false;

	public WirelessNetwork(){}
	public WirelessNetwork(ScanResult scanResult) {
		macAddress = scanResult.BSSID;
		ssid = scanResult.SSID;
		capabilities = scanResult.capabilities;
		frequency = scanResult.frequency;
		dataHash = buildHash(scanResult);
	}

	public void update(ScanResult scanResult) {
		// base stuff
		macAddress = scanResult.BSSID;
		ssid = scanResult.SSID;
		capabilities = scanResult.capabilities;
		frequency = scanResult.frequency;
		dataHash = buildHash(scanResult);

		// update specific
		lastUpdate = Converter.dateToString(Calendar.getInstance().getTime());
		updateCount++;
		updatedData = true;
	}

	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getDataHash() {
		return dataHash;
	}
	public void setDataHash(String dataHash) {
		this.dataHash = dataHash;
	}

	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	public boolean isNewData() {
		return newData;
	}
	public void setNewData(boolean newData) {
		this.newData = newData;
	}

	public boolean isUpdatedData() {
		return updatedData;
	}
	public void setUpdatedData(boolean updatedData) {
		this.updatedData = updatedData;
	}

	public static String buildHash(ScanResult scanResult) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(scanResult.SSID.trim().toLowerCase())
				.append(scanResult.frequency)
				.append(scanResult.capabilities);

		return Converter.toSha256(stringBuilder.toString());
	}
}
