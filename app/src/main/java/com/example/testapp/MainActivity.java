package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adhelper.StartContext;


public class MainActivity extends AppCompatActivity {

    private Button watchAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        watchAd = findViewById(R.id.adHelperButton);

        watchAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdHelper.class);
                intent.putExtra("StartContext", StartContext.RANDOM.getCode());
                startActivity(intent);
            }
        });
    }


}