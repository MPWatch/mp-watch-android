package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Topic {
    @PrimaryKey
    private String name;
    private int count;
}
