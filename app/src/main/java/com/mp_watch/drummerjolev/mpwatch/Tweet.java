package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Tweet {
    @PrimaryKey
    @SerializedName("tweet_id")
    @Expose
    private Long id;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("created")
    @Expose
    private Date created;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("followers_count")
    @Expose
    private int followersCount;
    @SerializedName("profile_pic_link")
    @Expose
    private String profilePicLink;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("retweet_count")
    @Expose
    private int retweetCount;
    @SerializedName("twitter_handle")
    @Expose
    private String twitterHandle;
    @SerializedName("url")
    @Expose
    private String url;

    public Long getId() {
        return id;
    }

    public String getAdded() {
        return added;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }

    public String getEntity() {
        return entity;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public void setProfilePicLink(String profilePicLink) {
        this.profilePicLink = profilePicLink;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
