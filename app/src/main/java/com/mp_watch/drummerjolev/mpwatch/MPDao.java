package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MPDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<MP> mps);
    @Query("SELECT * FROM mp WHERE name = :name")
    LiveData<List<MP>> loadPerName(String name);
    @Query("SELECT * FROM mp")
    LiveData<List<MP>> loadAll();
}
