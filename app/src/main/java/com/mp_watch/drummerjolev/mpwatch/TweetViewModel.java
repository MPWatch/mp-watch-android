package com.mp_watch.drummerjolev.mpwatch;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.List;

public class TweetViewModel extends ViewModel {
    private TweetsRepository tweetsRepository;
    private LiveData<List<Topic>> topics;
    private MutableLiveData<Topic> currentTopic;
    private MutableLiveData<String> currentSearchQueryMP;
    private LiveData<MP> currentMP;
    private QueryLiveData query;
    private LiveData<List<Tweet>> tweets;

    public TweetViewModel() {
        // TODO: change to @Inject with Dagger
        this.tweetsRepository = new TweetsRepository();
    }

    public void init() {
        Log.d("view model", "launching view model");
        this.topics = tweetsRepository.getTopics();
        // TODO: add call to get all MPs
        this.currentTopic = new MutableLiveData<>();
        this.currentSearchQueryMP = new MutableLiveData<>();
        this.query = new QueryLiveData(currentTopic, currentSearchQueryMP);
        this.tweets = Transformations.switchMap(query, new Function<Pair<Topic, String>, LiveData<List<Tweet>>>() {
            @Override
            public LiveData<List<Tweet>> apply(Pair<Topic, String> input) {
                Topic topic = input.first;
                String mp = input.second;
                Log.d("transformation", "applying transformation to topic: " + topic.getName());
                Log.d("transformation", "applying transformation to mp: " + mp);
                return tweetsRepository.getTweets(topic, mp);
            }
        });
        this.currentMP = Transformations.switchMap(currentSearchQueryMP, new Function<String, LiveData<MP>>() {
            @Override
            public LiveData<MP> apply(String input) {
                Log.d("transformation", "looking for an mp matching name " + input);
                return tweetsRepository.getMP(input);
            }
        });
    }

    public LiveData<List<Topic>> getTopics() {
        return topics;
    }

    public void setCurrentTopic(Topic currentTopic) {
        this.currentTopic.setValue(currentTopic);
    }

    public void setCurrentSearchQueryMP(String mp) {
        // set to null when done
        this.currentSearchQueryMP.setValue(mp);
    }

    public LiveData<MP> getCurrentMP() {
        return currentMP;
    }

    public String getCurrentSearchQueryMP() {
        return currentSearchQueryMP.getValue();
    }

    public Topic getCurrentTopic() {
        return currentTopic.getValue();
    }

    public LiveData<List<Tweet>> getTweets() {
        return tweets;
    }
}
