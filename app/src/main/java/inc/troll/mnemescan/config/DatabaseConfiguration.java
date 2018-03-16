package inc.troll.mnemescan.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import inc.troll.mnemescan.data.NetworkCoordinates;
import inc.troll.mnemescan.data.WirelessNetwork;
import inc.troll.mnemescan.data.repositories.NetworkCoordinatesRepository;
import inc.troll.mnemescan.data.repositories.WirelessNetworkRepository;

@Database(entities = {WirelessNetwork.class, NetworkCoordinates.class}, version = 1, exportSchema = false)
public abstract class DatabaseConfiguration extends RoomDatabase {
	public abstract WirelessNetworkRepository getWirelessNetworkRepository();
	public abstract NetworkCoordinatesRepository getNetworkCoordinatesRepository();
}
