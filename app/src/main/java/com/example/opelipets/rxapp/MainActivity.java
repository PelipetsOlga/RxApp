package com.example.opelipets.rxapp;

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

        Button btnDo = (Button) findViewById(R.id.btn_do_smth);
        btnDo.setOnClickListener(v -> Toast.makeText(MainActivity.this, "button was pressed", Toast.LENGTH_SHORT).show());
    }
}
