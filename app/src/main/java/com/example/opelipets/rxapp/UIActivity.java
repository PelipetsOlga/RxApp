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

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class UIActivity extends AppCompatActivity {

    Observable<String> nameEmitter;
    Observable<String> emailEmitter;
    CompositeDisposable compositeDisposable;
    ActivityUiBinding binding;
    ScreenModel screenModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui);
        screenModel = new ScreenModel();
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
        Observable<Boolean> btnStateEmitter = Observable.combineLatest(nameEmitter, emailEmitter,
                (x, y) -> isValidName(x) && isValidEmail(y));
        compositeDisposable.add(btnStateEmitter.map(bool -> setButtonEnable(bool)).subscribe());
    }

    private boolean setButtonEnable(boolean bool) {
        screenModel.btnEnabled.set(bool);
        return bool;
    }

    private void doNameValidation(String name) {
        if (!isValidName(name)) binding.nameInput.setError("Name shouldn't be empty");
    }

    private boolean isValidName(String name) {
        return !TextUtils.isEmpty(name);
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && email.contains("@") && email.contains(".");
    }

    private void doEmailValidation(String email) {
        if (!isValidEmail(email)) binding.emailInput.setError("Email shouldn't be empty");
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
