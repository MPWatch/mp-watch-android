package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Tweet> tweets);
    @Query("SELECT * FROM tweet WHERE entity = :topic")
    LiveData<List<Tweet>> loadPerTopic(String topic);
    @Query("SELECT * FROM tweet WHERE entity = :topic AND twitterHandle = :mp")
    LiveData<List<Tweet>> load(String topic, String mp);
    // TODO: add query taking topic, mp as parameter
    @Query("SELECT * FROM tweet")
    LiveData<List<Tweet>> loadAll();
    @Query("SELECT COUNT(*) FROM tweet WHERE entity = :topic")
    int count(String topic);
}
