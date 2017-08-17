package com.example.opelipets.rxapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.opelipets.rxapp.databinding.ActivityUiBinding;

public class UIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUiBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ui);
        ScreenModel screenModel = new ScreenModel();
        binding.setModel(screenModel);
    }
}
