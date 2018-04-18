package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class TweetsRepository {
    private Webservice webservice;
    private MutableLiveData<ArrayList<Topic>> topics;
    private MutableLiveData<ArrayList<Tweet>> tweets;

    public TweetsRepository() {
        // init LiveData objects
        topics = new MutableLiveData<>();
        tweets = new MutableLiveData<>();
        // add logger to API calls
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logger);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mp-watch.lifeissababa.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        this.webservice = retrofit.create(Webservice.class);
    }

    public LiveData<ArrayList<Topic>> getTopics() {
        webservice.getTopics().enqueue(new Callback<ArrayList<Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Topic>> call, Response<ArrayList<Topic>> response) {
                topics.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Topic>> call, Throwable t) {
                // TODO: throw error
                Log.d("tweets repo", "topic fetch failed " + t.getLocalizedMessage());
            }
        });
        return topics;
    }

    public LiveData<ArrayList<Tweet>> getTweets(Topic topic) {
        // topic can be null (on initial call)
        if (topic != null) {
            webservice.getTweets(topic.getName()).enqueue(new Callback<ArrayList<Tweet>>() {
                @Override
                public void onResponse(Call<ArrayList<Tweet>> call, Response<ArrayList<Tweet>> response) {
                    tweets.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Tweet>> call, Throwable t) {
                    // TODO: throw error
                    Log.d("tweets repo", "tweets fetch failed " + t.getMessage());
                }
            });
        }
        return tweets;
    }
}
