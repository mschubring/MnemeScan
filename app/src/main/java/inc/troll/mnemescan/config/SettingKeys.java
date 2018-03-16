package inc.troll.mnemescan.config;

public enum SettingKeys {
	SCAN_SWITCH("setting.scan.switch"),
	SCAN_FREQUENCY("setting.scan.frequence")
	;

	private final String key;

	private SettingKeys(final String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return key;
	}
}
