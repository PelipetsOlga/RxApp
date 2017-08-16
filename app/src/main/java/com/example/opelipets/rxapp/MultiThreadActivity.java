package com.example.opelipets.rxapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MultiThreadActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);
        tv = (TextView) findViewById(R.id.tv);

        findViewById(R.id.btn_clear).setOnClickListener(v -> clear());
        findViewById(R.id.btn_1).setOnClickListener(v -> execute1());
    }

    private void clear() {
        tv.setText("");
    }

    private void execute1() {
        tv.setText("test 1");
    }
}
