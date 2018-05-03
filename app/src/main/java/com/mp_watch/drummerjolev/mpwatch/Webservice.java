package com.mp_watch.drummerjolev.mpwatch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {
    @GET("/topics")
    Call<ArrayList<Topic>> getTopics();

    @GET("/mps")
    Call<ArrayList<MP>> getMPs();

    @GET("/tweets")
    Call<ArrayList<Tweet>> getTweets(@Query("topic") String topicName);
}
