package com.example.opelipets.rxapp;

import android.os.StrictMode;
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

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4, 5, 6 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // it will emit 4, 5, 6 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onNext(5);
        source.onNext(6);

        // it will emit only onComplete for third observer.
        source.subscribe(getThirdObserver());

        source.onComplete();
    }

    private void replaySubject() {
        ReplaySubject<Integer> source = ReplaySubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4, 5, 6 and Complete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);

        //it will emit 1, 2, 3, 4, 5, 6 for second observer also as we have used replay
        source.subscribe(getSecondObserver());

        source.onNext(5);
        source.onNext(6);
        source.onComplete();

        //it will emit 1, 2, 3, 4, 5, 6 for third observer also as we have used replay
        source.subscribe(getThirdObserver());

    }

    private void behaviourSubjeect() {
        BehaviorSubject<Integer> source = BehaviorSubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4, 5, 6 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // it will emit 3(last emitted), 4, 5, 6 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onNext(5);

        // it will emit 5(last emitted), 6 and onComplete for second observer also.
        source.subscribe(getThirdObserver());

        source.onNext(6);
        source.onComplete();
    }

    private void asynkSubject() {
        AsyncSubject<Integer> source = AsyncSubject.create();

        source.subscribe(getFirstObserver()); // it will emit only 6 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // it will emit 6 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onNext(5);


        // it will emit 6 and onComplete for third observer also.
        source.subscribe(getThirdObserver());

        source.onNext(6);
        source.onComplete();
    }

    protected abstract class BaseObserver implements Observer<Integer> {

        protected abstract String getNumber();

        @Override
        public void onSubscribe(Disposable d) {
            textView.append(getNumber() + "onSubscribe ");
            textView.append(LINE_SEPARATOR);
        }

        @Override
        public void onNext(Integer value) {
            textView.append(getNumber() + "onNext : value : " + value);
            textView.append(LINE_SEPARATOR);
        }

        @Override
        public void onError(Throwable e) {
            textView.append(getNumber() + "onError : " + e.getMessage());
            textView.append(LINE_SEPARATOR);
        }

        @Override
        public void onComplete() {
            textView.append(getNumber() + "onComplete");
            textView.append(LINE_SEPARATOR);
        }
    }

    private Observer<Integer> getFirstObserver() {
        return new BaseObserver() {
            @Override
            protected String getNumber() {
                return "First ";
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new BaseObserver() {
            @Override
            protected String getNumber() {
                return "   Second ";
            }
        };
    }

    private Observer<Integer> getThirdObserver() {
        return new BaseObserver() {
            @Override
            protected String getNumber() {
                return "      Third ";
            }
        };
    }
}
