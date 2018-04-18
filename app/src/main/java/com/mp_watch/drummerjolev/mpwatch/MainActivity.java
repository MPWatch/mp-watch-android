package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TweetViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        viewModel.init();

        // Observers
        viewModel.getTopics().observe(this, new Observer<ArrayList<Topic>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Topic> topics) {
                onTopicsChanged(topics);
            }
        });
        viewModel.getTweets().observe(this, new Observer<ArrayList<Tweet>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Tweet> tweets) {
                onTweetsChanged(tweets);
            }
        });
    }

    private void onTopicsChanged(ArrayList<Topic> topics) {
        Log.d("main activity", "updated topics");
        Topic t = topics.get(0);
        Log.d("its a topic", t.getName());
        viewModel.setCurrentTopic(t);
    }

    private void onTweetsChanged(ArrayList<Tweet> tweets) {
        Log.d("main activity", "updated tweets");
        for (Tweet t: tweets) {
            Log.d("its a tweet", t.getContent());
        }
    }
}
