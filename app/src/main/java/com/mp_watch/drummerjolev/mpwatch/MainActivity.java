package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class MainActivity extends
        AppCompatActivity implements
        TopicAdapter.TopicClickListener,
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener
{
    private TweetViewModel viewModel;

    private Handler searchHandler;

    private RecyclerView topicRecyclerView;
    private TopicAdapter topicAdapter;
    private RecyclerView tweetRecyclerView;
    private TweetAdapter tweetAdapter;

    private TextView emptyTweetRecyclerViewTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Views
        setContentView(R.layout.main_activity);
        initViews();

        // Handlers
        initHandlers();

        // ViewModel
        viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        viewModel.init();

        // Observers
        initObservers();
    }

    private void initHandlers() {
        searchHandler = new Handler();
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
        viewModel.getCurrentMP().observe(this, new Observer<MP>() {
            @Override
            public void onChanged(@Nullable MP mp) {
                onCurrentMPChanged(mp);
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

        // TextViews
        emptyTweetRecyclerViewTextView = findViewById(R.id.emptyRvText);
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
        if (viewModel != null) {
            viewModel.setCurrentSearchQueryMP("");
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        if (viewModel != null) {
            // cf. onTweetsChanged
            searchHandler.removeCallbacksAndMessages(null);
            searchHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("search", "runnable fires with " + newText);
                    viewModel.setCurrentSearchQueryMP(newText);
                }
            }, 1000);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void onCurrentMPChanged(final MP currentMP) {
        if (emptyTweetRecyclerViewTextView != null) {
            // update TextView
            if (currentMP != null) {
                String mpName = currentMP.getName();
                final String mpTwitterHandle = currentMP.getTwitterHandle();

                String displayText = String.format(getResources().getString(R.string.no_mp_response_text), mpName, mpTwitterHandle);
                emptyTweetRecyclerViewTextView.setText(displayText);

                emptyTweetRecyclerViewTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String topicName = viewModel.getCurrentTopic().getName();
                        String tweetText = String.format(getResources().getString(R.string.tweet_text), mpTwitterHandle, topicName);
                        String encodedTweetText = Uri.encode(tweetText);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=" + encodedTweetText));
                        startActivity(browserIntent);
                    }
                });
            } else {
                String searchQuery = viewModel.getCurrentSearchQueryMP();
                String displayQuery = getString(R.string.loading_text);
                if (searchQuery != null && !searchQuery.equals("")) {
                    displayQuery = String.format(getResources().getString(R.string.empty_query_text), searchQuery);
                }
                emptyTweetRecyclerViewTextView.setText(displayQuery);
                emptyTweetRecyclerViewTextView.setOnClickListener(null);
            }
        }
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
        // reset visibility on RV, aux views
        emptyTweetRecyclerViewTextView.setVisibility(View.INVISIBLE);
        tweetRecyclerView.setVisibility(View.VISIBLE);

        // update Adapter
        tweetAdapter.refreshAll(tweets);

        // change visibility on RV, aux views
        if (tweetAdapter.getItemCount() < 1) {
            tweetRecyclerView.setVisibility(View.INVISIBLE);
            emptyTweetRecyclerViewTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTopicClick(View view, Topic topic, int position) {
        viewModel.setCurrentTopic(topic);
    }
}
