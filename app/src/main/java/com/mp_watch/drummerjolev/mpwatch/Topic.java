package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Topic {
    @PrimaryKey
    @SerializedName("topic")
    @Expose
    private String name;

    @SerializedName("count")
    @Expose
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
