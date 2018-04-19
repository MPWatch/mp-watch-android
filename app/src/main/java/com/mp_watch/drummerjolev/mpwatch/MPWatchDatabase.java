package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

// Singleton implements database
@Database(entities = {Topic.class, Tweet.class}, version = 2)
public abstract class MPWatchDatabase extends RoomDatabase {
    private static MPWatchDatabase db;

    public abstract TopicDao topicDao();
    public abstract TweetDao tweetDao();

    public static MPWatchDatabase getInstance(Context context) {
        if (db == null) { db = buildInstance(context); }
        return db;
    }

    private static MPWatchDatabase buildInstance(Context context) {
        return Room.databaseBuilder(context,
                MPWatchDatabase.class,
                "db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public void cleanUp() {
        db = null;
    }
}
