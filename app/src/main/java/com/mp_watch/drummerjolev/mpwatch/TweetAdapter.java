package com.mp_watch.drummerjolev.mpwatch;

import android.content.Context;
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


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView content;
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.tweetText);
            image = view.findViewById(R.id.tweetPic);
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
        Tweet tweet = tweets.get(position);
        holder.content.setText(tweet.getContent());
        Glide.with(context).load(tweet.getProfilePicLink()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
