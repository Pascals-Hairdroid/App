package SimpleSync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SimpleSyncAdapterService extends Service {

    private String TAG = "SimpleSyncAdapterService";
    @Override
    public IBinder onBind(Intent intent) {
        SimpleSyncAdapter ssa = new SimpleSyncAdapter(getApplicationContext(),true);
        return ssa.getSyncAdapterBinder();
    }
}
