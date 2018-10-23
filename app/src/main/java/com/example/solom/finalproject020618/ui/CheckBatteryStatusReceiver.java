package com.example.solom.finalproject020618.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class CheckBatteryStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//system will show a message once the device's battery is 30%
        if (intent != null){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            if (level == 30){
                Toast.makeText(context, "Batter level " + level + "% is very low. Please connect to a charger.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
