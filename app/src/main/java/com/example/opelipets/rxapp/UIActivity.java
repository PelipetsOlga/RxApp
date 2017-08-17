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

import java.util.concurrent.TimeUnit;

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
        compositeDisposable.add(
                Observable.combineLatest(nameEmitter,
                        emailEmitter
//                                .debounce(500, TimeUnit.MILLISECONDS)// it's cause of CalledFromWrongThreadException
                        ,(x, y) -> doValidation(x, y))
                        .map(bool -> setButtonEnable(bool))
                        .subscribe());
    }

    private boolean setButtonEnable(boolean bool) {
        screenModel.btnEnabled.set(bool);
        return bool;
    }

    private boolean doValidation(String name, String email) {
        return doNameValidation(name) & doEmailValidation(email);
    }

    private boolean doNameValidation(String name) {
        boolean isValid = isValidName(name);
        if (!isValid) binding.nameInput.setError("Name shouldn't be empty");
        return isValid;
    }

    private boolean doEmailValidation(String email) {
        boolean isValid = isValidEmail(email);
        if (!isValid) binding.emailInput.setError("Email shouldn't be empty");
        return isValid;
    }

    private boolean isValidName(String name) {
        return !TextUtils.isEmpty(name);
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && email.contains("@") && email.contains(".");
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
