package inc.troll.mnemescan.data.repositories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import inc.troll.mnemescan.data.NetworkCoordinates;
import inc.troll.mnemescan.data.WirelessNetwork;

@Dao
public interface NetworkCoordinatesRepository {

//	@Insert
//	void insert(List<NetworkCoordinates> coordinatesList);

//	@Update
//	int update(List<NetworkCoordinates> coordinatesList);

	@Insert
	void insert(NetworkCoordinates coordinates);

	@Query("SELECT * FROM network_coordinates WHERE mac_address = :macAddress")
	List<NetworkCoordinates> findAllByMacAddress(String macAddress);

	@Query("SELECT * FROM network_coordinates WHERE mac_address = :macAddress AND latitude = :latitude AND longitude = :longitude LIMIT 1")
	NetworkCoordinates findByMacAddressAndCoordinates(String macAddress, float latitude, float longitude);

	@Query("SELECT * FROM network_coordinates WHERE is_new = 1")
	List<NetworkCoordinates> findAllNew();

	@Query("UPDATE network_coordinates SET is_new = 0 WHERE id IN(:ids)")
	int markAsTransferred(List<Long> ids);
}
