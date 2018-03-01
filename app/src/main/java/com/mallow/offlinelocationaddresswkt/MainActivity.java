package com.mallow.offlinelocationaddresswkt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String currentAddress = OfflineLocationUtils.getLocationAddress(MainActivity.this);
        Log.d(TAG, "currentAddress: " + currentAddress);
    }
}
