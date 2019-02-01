package com.example.opriday.homeremedies.Model.Youtube.PlayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Opriday on 12/25/2018.
 */

public class PlayListId {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("playlistId")
    @Expose
    private String playlistId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
