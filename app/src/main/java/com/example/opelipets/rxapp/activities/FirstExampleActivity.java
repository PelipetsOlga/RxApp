package com.example.opelipets.rxapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.opelipets.rxapp.R;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class FirstExampleActivity extends AppCompatActivity {
    TextView textView;

    Observable<String> observable = Observable.create(e -> {
        e.onNext("Hello, ");
        e.onNext("Wonderful");
        e.onNext("World!");
        e.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_example);
        textView = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doSomeWork();
    }

    void doSomeWork() {
        Disposable disposable = observable.subscribe(s -> print(s),
                error -> print(error.toString()),
                () -> print("complete"));
        disposable.dispose();
    }

    private void print(String s) {
        textView.append("\n" + s);
    }
}
