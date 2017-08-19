package com.example.opelipets.rxapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.opelipets.rxapp.R;
import com.example.opelipets.rxapp.api.GitHubService;
import com.example.opelipets.rxapp.api.NetworkUtils;
import com.example.opelipets.rxapp.model.Contributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.button).setOnClickListener(view -> {

            GitHubService gitHubService = new Retrofit.Builder().
                    baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(NetworkUtils.getUnsafeOkHttpClient())
                    .build()
                    .create(GitHubService.class);

            Call<List<Contributor>> call = gitHubService.repoContributors("square", "retrofit");
            call.enqueue(new Callback<List<Contributor>>() {
                @Override
                public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                    textView.setText(response.body().toString());
                }

                @Override
                public void onFailure(Call<List<Contributor>> call, Throwable t) {
                    textView.setText("Something went wrong: " + t.getMessage());
                }
            });
        });

    }


}
