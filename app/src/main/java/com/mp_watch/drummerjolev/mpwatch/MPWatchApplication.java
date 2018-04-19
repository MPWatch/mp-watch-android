package com.mp_watch.drummerjolev.mpwatch;

import android.app.Application;

public class MPWatchApplication extends Application {
    private static MPWatchApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static MPWatchApplication getContext() {
        return mContext;
    }
}
