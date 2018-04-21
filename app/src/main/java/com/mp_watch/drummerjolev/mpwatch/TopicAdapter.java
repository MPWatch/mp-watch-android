package com.mp_watch.drummerjolev.mpwatch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Topic> topics;
    private int currentTopicPosition;
    private TopicClickListener topicClickListener;

    public TopicAdapter(Context context, List<Topic> topics) {
        inflater = LayoutInflater.from(context);
        this.topics = topics;
        currentTopicPosition = 0;
    }

    public void setTopicClickListener(TopicClickListener topicClickListener) {
        this.topicClickListener = topicClickListener;
    }

    public interface TopicClickListener {
        void onTopicClick(View view, Topic topic, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.topicName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (topicClickListener != null && position != currentTopicPosition) {
                currentTopicPosition = position;
                notifyDataSetChanged();
                topicClickListener.onTopicClick(view, topics.get(position), position);
            }
        }
    }

    public void refreshAll(List<Topic> topics) {
        this.topics.clear();
        this.topics = topics;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.topic_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.textView.setText(topic.getName() + " (" + topic.getCount() + ")");
        int textWeight = position == currentTopicPosition ? Typeface.BOLD : Typeface.NORMAL;
        holder.textView.setTypeface(null, textWeight);
    }

    @Override
    public int getItemCount() {
        if (topics == null) {
            return 0;
        }
        return topics.size();
    }
}
