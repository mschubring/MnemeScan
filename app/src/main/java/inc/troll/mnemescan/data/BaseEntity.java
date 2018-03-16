package inc.troll.mnemescan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

import inc.troll.mnemescan.util.Converter;

public class BaseEntity {

	@PrimaryKey(autoGenerate = true)  // default : false ... are you fucking serious google!?!?!
	@ColumnInfo(name = "id")
	protected long id;

	@ColumnInfo(name = "created_at")
	protected String createdAt = Converter.dateToString(Calendar.getInstance().getTime());;

	@ColumnInfo(name = "last_update")
	protected String lastUpdate;

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
}
