package com.example.opelipets.rxapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.opelipets.rxapp.R;
import com.example.opelipets.rxapp.api.GitHubRxService;
import com.example.opelipets.rxapp.api.NetworkUtils;
import com.example.opelipets.rxapp.model.Contributor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRxActivity extends AppCompatActivity {
    TextView textView;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_rx);
        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.button_fetch_rx).setOnClickListener(view -> {

            GitHubRxService gitHubService = new Retrofit.Builder().
                    baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(NetworkUtils.getUnsafeOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GitHubRxService.class);
            gitHubService
                    .repoContributors("square", "retrofit")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Contributor>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(List<Contributor> result) {
                            textView.setText(result.toString());
                        }

                        @Override
                        public void onError(Throwable e) {
                            textView.setText("Error: " + e.toString());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        });
    }

    @Override
    protected void onStop() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onStop();
    }
}
