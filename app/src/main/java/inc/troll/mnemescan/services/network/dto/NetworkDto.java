package inc.troll.mnemescan.services.network.dto;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import inc.troll.mnemescan.data.WirelessNetwork;

public class NetworkDto {

	@SerializedName("id")
	private long id;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("last_update")
	private String lastUpdate;

	@SerializedName("mac_address")
	private String macAddress;

	@SerializedName("ssid")
	private String ssid;

	@SerializedName("frequency")
	private int frequency;

	@SerializedName("capabilities")
	private String capabilities;

	@SerializedName("update_count")
	private int updateCount;

	public NetworkDto(WirelessNetwork network) {
		this.id = network.getId();
		this.createdAt = network.getCreatedAt();
		this.lastUpdate = network.getLastUpdate();
		this.macAddress = network.getMacAddress();
		this.ssid = network.getSsid();
		this.frequency = network.getFrequency();
		this.capabilities = network.getCapabilities();
		this.updateCount = network.getUpdateCount();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
}
