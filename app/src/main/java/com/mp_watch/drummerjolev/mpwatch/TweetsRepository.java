package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class TweetsRepository {
    private final Webservice webservice;
    private final TopicDao topicDao;
    private final MPDao mpDao;
    private final TweetDao tweetDao;
    private final Executor executor;

    public TweetsRepository() {
        // add logger to API calls
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logger);
        httpClient.readTimeout(60, TimeUnit.SECONDS);

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
        this.mpDao = MPWatchDatabase.getInstance(MPWatchApplication.getContext()).mpDao();

        // Executor
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<MP>> getMPs() {
        fetchMPs();
        return mpDao.loadAll();
    }

    public LiveData<MP> getMP(String name) {
        int c = mpDao.count();
        if (c == 0) {
            fetchMPs();
        }
        // TODO: assuming all names are unique.
        // In reality, multiple MPs could have the same name.
        return mpDao.loadPerName(StringUtils.capitalizeString(name));
    }

    private void fetchMPs() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ArrayList<MP>> response = webservice.getMPs().execute();
                    mpDao.saveAll(response.body());
                } catch (Exception e) {
                    Log.d("mps repo", "mp fetch failed " + e.getLocalizedMessage());
                }
            }
        });
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

    public LiveData<List<Tweet>> getTweets(Topic topic, String mpQuery) {
        // topic can be null (on initial call)
        int c = tweetDao.count(topic.getName());
        if (c == 0) {
            fetchTweets(topic);
        }
        if (mpQuery != null && !mpQuery.equals("")) {
            return tweetDao.load(topic.getName(), mpQuery + "%");
        }
        return tweetDao.loadPerTopic(topic.getName());
    }

    private void fetchTweets(final Topic topic) {
        if (topic.getName() == null || topic.getName().equals("")) {
            return;
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Executor is fetching", "nowww");
                    Response<ArrayList<Tweet>> response = webservice.getTweets(topic.getName()).execute();
                    tweetDao.saveAll(response.body());
                } catch (Exception e) {
                    Log.d("tweets repo", "tweets fetch failed " + e.getLocalizedMessage());
                }
            }
        });
    }
}
