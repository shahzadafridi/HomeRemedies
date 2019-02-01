package com.example.opriday.homeremedies.Model.Youtube.PlayListVideos;

/**
 * Created by Opriday on 12/25/2018.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class PlayListVideoInfo {
    @SerializedName("items")
    @Expose
    private List<PlayListVideoItem> items = null;

    public List<PlayListVideoItem> getItems() {
        return items;
    }

    public void setItems(List<PlayListVideoItem> items) {
        this.items = items;
    }
 }