package inc.troll.mnemescan.data.repositories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import inc.troll.mnemescan.data.WirelessNetwork;

@Dao
public interface WirelessNetworkRepository {

	@Insert
	void insert(WirelessNetwork wirelessNetwork);

	@Update
	int update(WirelessNetwork wirelessNetwork);

	@Query("SELECT * FROM wireless_networks WHERE mac_address = :macAddress LIMIT 1")
	WirelessNetwork findByMacAddress(String macAddress);

	@Query("SELECT * FROM wireless_networks WHERE mac_address IN(:macAddress)")
	List<WirelessNetwork> findByMacAddressList(List<String> macAddress);

	@Query("SELECT * FROM wireless_networks WHERE is_new = 1")
	List<WirelessNetwork> findAllNew();

	@Query("SELECT * FROM wireless_networks WHERE is_new = 0 AND is_updated = 1")
	List<WirelessNetwork> findAllUpdated();

	@Query("UPDATE wireless_networks SET is_new = 0, is_updated = 0 WHERE id IN(:ids)")
	int markAsTransferred(List<Long> ids);
}
