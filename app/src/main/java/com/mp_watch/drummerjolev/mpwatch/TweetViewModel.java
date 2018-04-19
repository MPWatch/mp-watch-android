package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

public class TweetViewModel extends ViewModel {
    private TweetsRepository tweetsRepository;
    private LiveData<List<Topic>> topics;
    private Topic currentTopic;
    private LiveData<List<Tweet>> tweets;

    public TweetViewModel() {
        // TODO: change to @Inject with Dagger
        this.tweetsRepository = new TweetsRepository();
    }

    public void init() {
        Log.d("view model", "launching view model");
        this.topics = tweetsRepository.getTopics();
        this.tweets = tweetsRepository.getTweets(currentTopic);
    }

    public LiveData<List<Topic>> getTopics() {
        return topics;
    }

    public void setCurrentTopic(Topic currentTopic) {
        this.currentTopic = currentTopic;
        this.tweets = tweetsRepository.getTweets(this.currentTopic);
    }

    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public LiveData<List<Tweet>> getTweets() {
        return tweets;
    }
}
