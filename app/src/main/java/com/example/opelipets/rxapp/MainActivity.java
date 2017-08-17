package com.example.opelipets.rxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_multithreading).setOnClickListener(v -> openMultiThreadExample());
    }

    private void openMultiThreadExample() {
        startActivity(new Intent(MainActivity.this, MultiThreadActivity.class));
    }
}
