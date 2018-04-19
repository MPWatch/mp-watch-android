package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// Singleton implements database
@Database(entities = {Topic.class}, version = 1)
public abstract class MPWatchDatabase extends RoomDatabase {
    private static MPWatchDatabase db;

    public abstract TopicDao topicDao();

    public static MPWatchDatabase getInstance(Context context) {
        if (db == null) { db = buildInstance(context); }
        return db;
    }

    private static MPWatchDatabase buildInstance(Context context) {
        return Room.databaseBuilder(context,
                MPWatchDatabase.class,
                "db")
                .allowMainThreadQueries()
                .build();
    }

    public void cleanUp() {
        db = null;
    }
}
