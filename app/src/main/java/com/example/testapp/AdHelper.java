package com.example.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.adhelper.AdInitializer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdHelper extends AppCompatActivity {

    private AdInitializer adInitializer;

    private String geolocation;

    private String adId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_helper);


        adInitializer = new AdInitializer(AdHelper.this, "your app token", R.id.webView);
        adInitializer.loadPreferences(getPreferences(MODE_PRIVATE));


        if (adId == null) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                //Background work here
                adInitializer.getAdIdFromDevice();
                this.adId = adInitializer.getAdId();
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        adInitializer.showAd();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (geolocation == null) {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        //Background work here
                        adInitializer.getGeolocationFromDevice();
                        this.geolocation = adInitializer.getGeolocation();
                    });
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        adInitializer.savePreferences(getPreferences(MODE_PRIVATE));
    }
}