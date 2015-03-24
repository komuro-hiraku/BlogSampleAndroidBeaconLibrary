package beacon.android.classmethod.jp.altbeaconlibrarysample;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 *
 */
public class BeaconApplication extends Application implements BootstrapNotifier {

    public static final String TAG = BeaconApplication.class.getSimpleName();

    // iBeaconのデータを認識するためのParserフォーマット
    public static final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    private RegionBootstrap regionBootstrap;

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // iBeaconのデータを受信できるようにParserを設定
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));

        // UUID, major, minorの指定はしない
        Region region = new Region("uuid-region-bootstrap-001", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);
    }

    @Override
    public void didEnterRegion(Region region) {
        // 領域に入場した
        Log.d(TAG, "Enter Region");
        Intent intent = new Intent(this, MyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void didExitRegion(Region region) {
        // 領域から退場した
        Log.d(TAG, "Exit Region");
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        // 入退場状態が変更された
        Log.d(TAG, "Determine State: " + i);
    }
}
