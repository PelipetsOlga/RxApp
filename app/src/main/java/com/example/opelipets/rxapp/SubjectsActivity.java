package com.example.opelipets.rxapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class SubjectsActivity extends AppCompatActivity {
    private static final String LINE_SEPARATOR = "\n";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        textView = (TextView) findViewById(R.id.tv);

        findViewById(R.id.btn_clear).setOnClickListener(v -> clear());
        findViewById(R.id.btn_subject_publish).setOnClickListener(v -> publishSubject());
        findViewById(R.id.btn_subject_replay).setOnClickListener(v -> replaySubject());
        findViewById(R.id.btn_subject_behavior).setOnClickListener(v -> behaviourSubjeect());
        findViewById(R.id.btn_subject_asynk).setOnClickListener(v -> asynkSubject());
    }

    private void clear() {
        textView.setText("");
    }

    private void publishSubject() {
        PublishSubject<Integer> source = PublishSubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // it will emit 4 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();
    }

    private void replaySubject() {
        ReplaySubject<Integer> source = ReplaySubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4 and Complete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        //it will emit 1, 2, 3, 4 for second observer also as we have used replay
        source.subscribe(getSecondObserver());

    }

    private void behaviourSubjeect() {
        BehaviorSubject<Integer> source = BehaviorSubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // it will emit 3(last emitted), 4 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();
    }

    private void asynkSubject() {
        AsyncSubject<Integer> source = AsyncSubject.create();

        source.subscribe(getFirstObserver()); // it will emit only 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // it will emit 4 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();
    }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                textView.append(" First onSubscribe ");
                textView.append(LINE_SEPARATOR);
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" First onNext : value : " + value);
                textView.append(LINE_SEPARATOR);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" First onError : " + e.getMessage());
                textView.append(LINE_SEPARATOR);
            }

            @Override
            public void onComplete() {
                textView.append(" First onComplete");
                textView.append(LINE_SEPARATOR);
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                textView.append(" Second onSubscribe");
                textView.append(LINE_SEPARATOR);
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" Second onNext : value : " + value);
                textView.append(LINE_SEPARATOR);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" Second onError : " + e.getMessage());
                textView.append(LINE_SEPARATOR);
            }

            @Override
            public void onComplete() {
                textView.append(" Second onComplete");
                textView.append(LINE_SEPARATOR);
            }
        };
    }
}
