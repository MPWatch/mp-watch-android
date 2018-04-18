package com.mp_watch.drummerjolev.mpwatch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class TweetsRepository {
    private Webservice webservice;

    public TweetsRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mp-watch-lifeissababa.com/")
                .build();
        this.webservice = retrofit.create(Webservice.class);
    }

    public LiveData<ArrayList<Topic>> getTopics() {
        final MutableLiveData<ArrayList<Topic>> data = new MutableLiveData<>();
        webservice.getTopics().enqueue(new Callback<ArrayList<Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Topic>> call, Response<ArrayList<Topic>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Topic>> call, Throwable t) {
                Log.d("tweets repo", "topic fetch failed");
            }
        });
        return data;
    }
}
