package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    private final Webservice webservice;
    private final TopicDao topicDao;
    private final TweetDao tweetDao;
    private final Executor executor;
    private MutableLiveData<List<Topic>> topics;
    private MutableLiveData<List<Tweet>> tweets;

    public TweetsRepository() {
        // init LiveData objects
        topics = new MutableLiveData<>();
        tweets = new MutableLiveData<>();

        // add logger to API calls
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logger);

        // Webservice
        // custom gson object for date formatting
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mp-watch.lifeissababa.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        this.webservice = retrofit.create(Webservice.class);

        // DAOs
        this.topicDao = MPWatchDatabase.getInstance(MPWatchApplication.getContext()).topicDao();
        this.tweetDao = MPWatchDatabase.getInstance(MPWatchApplication.getContext()).tweetDao();

        // Executor
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Topic>> getTopics() {
        fetchTopics();
        return topicDao.loadAll();
    }

    private void fetchTopics() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ArrayList<Topic>> response = webservice.getTopics().execute();
                    topicDao.saveAll(response.body());
                } catch (Exception e) {
                    Log.d("topics repo", "topics fetch failed " + e.getLocalizedMessage());
                }
            }
        });
    }

    public LiveData<List<Tweet>> getTweets(Topic topic) {
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
