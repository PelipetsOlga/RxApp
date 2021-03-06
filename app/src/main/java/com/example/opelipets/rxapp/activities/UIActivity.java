package com.example.opelipets.rxapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.opelipets.rxapp.R;
import com.example.opelipets.rxapp.databinding.ActivityUiBinding;
import com.example.opelipets.rxapp.viewmodel.ScreenModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class UIActivity extends AppCompatActivity {

    Observable<String> nameEmitter;
    Observable<String> emailEmitter;
    Observable<Boolean> sexEmitter;
    Observable<Integer> dayEmitter;
    CompositeDisposable compositeDisposable;
    ActivityUiBinding binding;
    ScreenModel screenModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui);
        screenModel = new ScreenModel(getResources().getStringArray(R.array.days));
        binding.setModel(screenModel);

        nameEmitter = getTextWatcherObservable(binding.nameInput);
        emailEmitter = getTextWatcherObservable(binding.emailInput);
        sexEmitter = getOnRadioGroupCheckedChangedObservable(binding.radioGroup);
        dayEmitter = getOnSpinnerSelectedValueChangedObservable(binding.spinner);

    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                Observable.combineLatest(nameEmitter,
                        emailEmitter
//                                .debounce(500, TimeUnit.MILLISECONDS)// it's cause of CalledFromWrongThreadException
                        , sexEmitter
                        , dayEmitter
                        , (x, y, bool, integer) -> doValidation(x, y, bool, integer))
                        .map(bool -> setButtonEnable(bool))
                        .filter(bool -> search(bool))
                        .subscribe());
    }

    private boolean setButtonEnable(boolean bool) {
        screenModel.btnEnabled.set(bool);
        return bool;
    }

    private boolean doValidation(String name, String email, Boolean bool, Integer position) {
        return doNameValidation(name) & doEmailValidation(email) & doDaysValidation(position);
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

    private boolean doDaysValidation(Integer position) {
        return position != 0;
    }

    private boolean search(boolean bool) {
        if (bool) {
            Observable.just(screenModel.getRequest())
                    .subscribeOn(Schedulers.io())
                    .map(s -> {
                        Thread.sleep(1000);
                        return "Request: " + s;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(s -> printResult(s))
                    .subscribe();
        }
        return bool;
    }

    private String printResult(String result) {
        screenModel.result.set(result);
        return result;
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

    public static Observable<Boolean> getOnRadioGroupCheckedChangedObservable(@NonNull final RadioGroup group) {

        final PublishSubject<Boolean> subject = PublishSubject.create();

        group.setOnCheckedChangeListener((group1, checkedId) -> {
            if (checkedId == R.id.rb_male) {
                subject.onNext(true);
            } else if (checkedId == R.id.rb_female) {
                subject.onNext(false);
            }
        });

        return subject;
    }

    public Observable<Integer> getOnSpinnerSelectedValueChangedObservable(@NonNull final AppCompatSpinner spinner) {

        final PublishSubject<Integer> subject = PublishSubject.create();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                screenModel.day.set(screenModel.days.get(position));
                subject.onNext(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return subject;
    }


}
