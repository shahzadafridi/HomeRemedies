package com.example.opriday.homeremedies.Model.Youtube.PlayList;

/**
 * Created by Opriday on 12/25/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayListItem {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private PlayListId id;
    @SerializedName("snippet")
    @Expose
    private PLayListSnipet snippet;

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

    public PlayListId getId() {
        return id;
    }

    public void setId(PlayListId id) {
        this.id = id;
    }

    public PLayListSnipet getSnippet() {
        return snippet;
    }

    public void setSnippet(PLayListSnipet snippet) {
        this.snippet = snippet;
    }

}