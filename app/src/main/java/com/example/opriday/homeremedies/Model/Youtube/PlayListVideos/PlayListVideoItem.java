package com.example.opriday.homeremedies.Model.Youtube.PlayListVideos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Opriday on 12/25/2018.
 */

public class PlayListVideoItem {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("snippet")
    @Expose
    private PlayListVideoSnippet snippet;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayListVideoSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(PlayListVideoSnippet snippet) {
        this.snippet = snippet;
    }

}