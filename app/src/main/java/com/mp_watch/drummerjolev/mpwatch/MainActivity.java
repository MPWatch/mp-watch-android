package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TweetViewModel viewModel;
    private RecyclerView recyclerView;
    private TopicAdapter topicAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Views
        setContentView(R.layout.main_activity);
        initViews();

        // ViewModel
        viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        viewModel.init();

        // Observers
        initObservers();
    }

    private void initObservers() {
        viewModel.getTopics().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(@Nullable List<Topic> topics) {
                onTopicsChanged(topics);
            }
        });
        viewModel.getTweets().observe(this, new Observer<List<Tweet>>() {
            @Override
            public void onChanged(@Nullable List<Tweet> tweets) {
                onTweetsChanged(tweets);
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rvTopics);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Log.d("init views", " " + recyclerView.toString());
        recyclerView.setLayoutManager(horizontalLayoutManager);
    }

    private void onTopicsChanged(List<Topic> topics) {
        Log.d("main activity", "updated topics");
        if (topics.size() > 0) {
            topicAdapter = new TopicAdapter(this, topics);
            recyclerView.setAdapter(topicAdapter);
            Topic t = topics.get(0);
            Log.d("updating topic", "" + t.getName());
            viewModel.setCurrentTopic(t);
        }
    }

    private void onTweetsChanged(List<Tweet> tweets) {
        Log.d("main activity", "updated tweets");
        Log.d("tweets info", "" + tweets.size());
        Log.d("tweets info more", "" + viewModel.getCurrentTopic().getValue().getName());
        for (Tweet t: tweets) {
            Log.d("its a tweet", t.getContent());
        }
    }
}
