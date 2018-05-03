package com.mp_watch.drummerjolev.mpwatch;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class MP {
    @PrimaryKey
    @NonNull
    @SerializedName("twitter_handle")
    @Expose
    private String twitterHandle;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("party")
    @Expose
    private String party;
    @SerializedName("constituency")
    @Expose
    private String constituency;

    @NonNull
    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(@NonNull String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }
}
