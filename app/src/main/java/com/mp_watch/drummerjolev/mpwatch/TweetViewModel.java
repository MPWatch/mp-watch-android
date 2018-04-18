package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

public class TweetViewModel extends ViewModel {
    private TweetsRepository tweetsRepository;
    private LiveData<ArrayList<Topic>> topics;
    private LiveData<ArrayList<Tweet>> tweets;

    public TweetViewModel() {
        // TODO: change to @Inject with Dagger
        this.tweetsRepository = new TweetsRepository();
    }

    public void init() {
        Log.d("view model", "launching view model");
        this.topics = tweetsRepository.getTopics();
    }

    public LiveData<ArrayList<Topic>> getTopics() {
        return topics;
    }

    public LiveData<ArrayList<Tweet>> getTweets(String topic) {
        Log.d("view model", "getting tweets for " + topic);
        return tweets;
    }
}
