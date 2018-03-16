package inc.troll.mnemescan.screens.home;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inc.troll.mnemescan.R;
import inc.troll.mnemescan.services.network.ScanDataTransferService;
import inc.troll.mnemescan.services.network.ScanWirelessNetworkService;

public class HomeActivity extends Activity {

	@BindView(R.id.ScanLogScrollView)
	ScrollView scanLogScrollView;

	@BindView(R.id.ScanLogText)
	TextView scanLogText;

	@BindView(R.id.ScanTrigger)
	Button scanTrigger;

	@BindView(R.id.SentData)
	Button sentData;

	private ScanWirelessNetworkService scanWirelessNetworkService;
	private ScanDataTransferService scanDataTransferService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
		ButterKnife.bind(this);

		scanWirelessNetworkService = new ScanWirelessNetworkService(this.getApplicationContext(), scanLogText);
		scanDataTransferService = new ScanDataTransferService(this.getApplicationContext());

		System.out.println(this.getApplication().getPackageResourcePath());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

	@OnClick(R.id.ScanTrigger)
	public void toggleScaning() {
    	if(scanWirelessNetworkService.isScanning()) {
			scanWirelessNetworkService.stopScan();
			scanTrigger.setText("Start Scaning");
		} else {
			scanWirelessNetworkService.startScan();
			scanTrigger.setText("Stop Scaning");
		}
	}

	@OnClick(R.id.SentData)
	public void transfereScanData() {
		scanDataTransferService.transferScanData();
	}
}
