package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements TopicAdapter.TopicClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private TweetViewModel viewModel;

    private RecyclerView topicRecyclerView;
    private TopicAdapter topicAdapter;
    private RecyclerView tweetRecyclerView;
    private TweetAdapter tweetAdapter;

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
        // Topics
        topicRecyclerView = findViewById(R.id.rvTopics);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topicRecyclerView.setLayoutManager(horizontalLayoutManager);
        topicAdapter = new TopicAdapter(this, Collections.<Topic>emptyList());
        topicAdapter.setTopicClickListener(this);
        topicRecyclerView.setAdapter(topicAdapter);

        // Tweets
        tweetRecyclerView = findViewById(R.id.rvTweets);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tweetRecyclerView.setLayoutManager(verticalLayoutManager);
        tweetAdapter = new TweetAdapter(this, Collections.<Tweet>emptyList());
        tweetRecyclerView.setAdapter(tweetAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // menu listener
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(this);
        // search query listener
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Log.d("search", "done searching");
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (viewModel != null) {
            // TODO: add search param to DB query
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void onTopicsChanged(List<Topic> topics) {
        Log.d("main activity", "updated topics");
        if (topics.size() > 0) {
            topicAdapter.refreshAll(topics);
            Topic t = topics.get(0);
            viewModel.setCurrentTopic(t);
            Log.d("updating topic", "" + t.getName());
        }
    }

    private void onTweetsChanged(List<Tweet> tweets) {
        tweetAdapter.refreshAll(tweets);
    }

    @Override
    public void onTopicClick(View view, Topic topic, int position) {
        viewModel.setCurrentTopic(topic);
    }
}
