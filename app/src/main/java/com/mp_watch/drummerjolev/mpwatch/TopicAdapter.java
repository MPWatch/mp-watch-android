package com.mp_watch.drummerjolev.mpwatch;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public TopicAdapter(Context context, List<Topic> topics) {
        inflater = LayoutInflater.from(context);
        this.topics = topics;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.topicName);
        }
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
        holder.textView.setText(topic.getName());
    }

    @Override
    public int getItemCount() {
        if (topics == null) {
            return 0;
        }
        return topics.size();
    }
}
