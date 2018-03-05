package inc.troll.mnemescan;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    // setting keys
    private static final String SETTING_SCAN_SWITCH = "setting.scan.switch";
    private static final String SETTING_SCAN_FREQUENCY = "setting.scan.frequence";

    // objects
    private ToggleButton scanSwitch;
    private SeekBar scanFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadElements();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO maybe reload load latest settings
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO maybe reload load latest settings
    }

    @Override
    protected void onStop() {
        super.onStop();

        // store app settings
        SharedPreferences settings = getSharedPreferences("ScannerSettings",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SETTING_SCAN_SWITCH, scanSwitch.isChecked());
        editor.putInt(SETTING_SCAN_FREQUENCY, scanFrequency.getProgress());
        editor.commit();
    }

    private void loadSettings() {
        SharedPreferences settings = getSharedPreferences("ScannerSettings",0);
        scanSwitch.setChecked(settings.getBoolean(SETTING_SCAN_SWITCH,false));
        scanFrequency.setProgress(settings.getInt(SETTING_SCAN_FREQUENCY,5));
    }

    private void loadElements() {
        scanSwitch = (ToggleButton) findViewById(R.id.ScanSwitch);
        scanFrequency = (SeekBar) findViewById(R.id.ScanFrequency);

        scanSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        loadSettings();
    }
}
