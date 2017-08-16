package com.example.opelipets.rxapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MultiThreadActivity extends AppCompatActivity {
    private static long startTime;
    private static long stopTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);

        findViewById(R.id.btn_1).setOnClickListener(v -> execute1());
        findViewById(R.id.btn_2).setOnClickListener(v -> execute2());
        findViewById(R.id.btn_3).setOnClickListener(v -> execute3());
        findViewById(R.id.btn_4).setOnClickListener(v -> execute4());
    }

    private void print(int i) {
        Log.d("RxApp", Thread.currentThread().getName() + "   " + i + "\n");
    }

    private void startTimer() {
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    private void stopTimer() {
        stopTime = Calendar.getInstance().getTimeInMillis();
        long dif = stopTime - startTime;
        Log.d("RxApp", "Timer " + dif);
    }

    private void execute1() {
        startTimer();
        Observable.range(1, 10)
                .subscribe(i -> print(i));
        stopTimer();
    }

    private void execute2() {
        startTimer();
        Observable.range(1, 10)
                .map(v -> calculation(v))
                .subscribe(i -> print(i));
        stopTimer();
    }

    private void execute3() {
        startTimer();
        Observable.range(1, 10)
                .map(v -> calculation(v))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())//without it CalledFromWrongThreadException
                .subscribe(i -> print(i));
    }

    private void execute4() {
        startTimer();
        Observable.range(1, 10)
                .flatMap(integer -> Observable.just(integer)
                        .map(v -> calculation(v))
                        .subscribeOn(Schedulers.computation())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> print(i));
    }

    private int calculation(int i) {
        try {
            int result = 2 * i;
            Thread.sleep(500);
            print(result);
            stopTimer();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
