package com.example.opelipets.rxapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.opelipets.rxapp.R;
import com.example.opelipets.rxapp.SubjectsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_multithreading).setOnClickListener(v -> openMultiThreadExample());
        findViewById(R.id.btn_ui).setOnClickListener(v -> openUIExample());
        findViewById(R.id.btn_http).setOnClickListener(v -> openHTTPExample());
        findViewById(R.id.btn_http_rx).setOnClickListener(v -> openHTTPRxExample());
        findViewById(R.id.btn_subjects).setOnClickListener(v -> openSubjectsExample());
    }

    private void openMultiThreadExample() {
        startActivity(new Intent(MainActivity.this, MultiThreadActivity.class));
    }

    private void openUIExample() {
        startActivity(new Intent(MainActivity.this, UIActivity.class));
    }

    private void openHTTPExample() {
        startActivity(new Intent(MainActivity.this, HttpActivity.class));
    }

    private void openHTTPRxExample() {
        startActivity(new Intent(MainActivity.this, HttpRxActivity.class));
    }

    private void openSubjectsExample() {
        startActivity(new Intent(MainActivity.this, SubjectsActivity.class));
    }
}
