package com.mp_watch.drummerjolev.mpwatch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Tweet> tweets;
    private TweetClickListener tweetClickListener;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        inflater = LayoutInflater.from(context);
        this.tweets = tweets;
        this.context = context;
    }

    public void refreshAll(List<Tweet> topics) {
        this.tweets.clear();
        this.tweets = topics;
        notifyDataSetChanged();
    }

    public void setTweetClickListener(TweetClickListener tweetClickListener) {
        this.tweetClickListener = tweetClickListener;
    }

    public interface TweetClickListener {
        void onTweetClick(View view, Tweet tweet, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView author;
        public TextView content;
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            author = view.findViewById(R.id.tweetHandleText);
            content = view.findViewById(R.id.tweetText);
            image = view.findViewById(R.id.tweetPic);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (tweetClickListener != null) {
                tweetClickListener.onTweetClick(v, tweets.get(position), position);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tweet_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        String authorInfo = String.format(context.getResources().getString(R.string.tweet_author), tweet.getName(), tweet.getTwitterHandle());
        holder.content.setText(tweet.getContent());
        holder.author.setText(authorInfo);
        Glide.with(context).load(tweet.getProfilePicLink()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
