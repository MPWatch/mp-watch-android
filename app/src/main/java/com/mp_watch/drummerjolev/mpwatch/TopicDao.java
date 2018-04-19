package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Topic> topic);
    @Query("SELECT * FROM topic")
    LiveData<List<Topic>> loadAll();
}
