package inc.troll.mnemescan.services.network.dto;

import com.google.gson.annotations.SerializedName;

import inc.troll.mnemescan.data.NetworkCoordinates;

public class CoordinatesDto {

	@SerializedName("id")
	private long id;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("last_update")
	private String lastUpdate;

	@SerializedName("mac_address")
	private String macAddress;

	@SerializedName("signal_strength")
	private int signalStrength;

	@SerializedName("latitude")
	private float latitude;

	@SerializedName("longitude")
	private float longitude;

	public CoordinatesDto(NetworkCoordinates coordinates) {
		this.id = coordinates.getId();
		this.createdAt = coordinates.getCreatedAt();
		this.lastUpdate = coordinates.getLastUpdate();
		this.macAddress = coordinates.getMacAddress();
		this.signalStrength = coordinates.getSignalStrength();
		this.latitude = coordinates.getLatitude();
		this.longitude = coordinates.getLongitude();
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
}
