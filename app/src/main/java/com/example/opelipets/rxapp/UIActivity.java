package com.example.opelipets.rxapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.opelipets.rxapp.databinding.ActivityUiBinding;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class UIActivity extends AppCompatActivity {

    Observable<String> nameEmitter;
    Observable<String> emailEmitter;
    CompositeDisposable compositeDisposable;
    ActivityUiBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui);
        ScreenModel screenModel = new ScreenModel();
        binding.setModel(screenModel);

        nameEmitter = getTextWatcherObservable(binding.nameInput);
        emailEmitter = getTextWatcherObservable(binding.emailInput);

    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(nameEmitter.subscribe(name -> doNameValidation(name)));
        compositeDisposable.add(emailEmitter.subscribe(email -> doEmailValidation(email)));
    }

    private void doNameValidation(String name) {
        if (TextUtils.isEmpty(name))binding.nameInput.setError("Name shouldn't be empty");
    }

    private void doEmailValidation(String email) {
        if (TextUtils.isEmpty(email))binding.emailInput.setError("Email shouldn't be empty");
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    public static Observable<String> getTextWatcherObservable(@NonNull final EditText editText) {

        final PublishSubject<String> subject = PublishSubject.create();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                subject.onNext(s.toString());
            }
        });

        return subject;
    }
}
