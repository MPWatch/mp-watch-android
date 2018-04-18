package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Tweet {
    @PrimaryKey
    private int id;
    private String content;
}
