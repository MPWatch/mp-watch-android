package com.mp_watch.drummerjolev.mpwatch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Webservice {
    @GET("/topics")
    Call<ArrayList<Topic>> getTopics();
}
