package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

public class QueryLiveData extends MediatorLiveData<Pair<Topic, String>> {
    // TODO: replace to
    public QueryLiveData(final LiveData<Topic> topic, final LiveData<String> mp) {
        addSource(topic, new Observer<Topic>() {
            @Override
            public void onChanged(@Nullable Topic topic) {
                setValue(Pair.create(topic, mp.getValue()));
            }
        });
        addSource(mp, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setValue(Pair.create(topic.getValue(), s));
            }
        });
    }
}
