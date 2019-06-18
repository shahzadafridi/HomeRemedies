package com.example.opriday.homeremedies.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("remedie_id")
    @Expose
    private Integer remedieId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("remedies")
    @Expose
    private Remedie remedie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRemedieId() {
        return remedieId;
    }

    public void setRemedieId(Integer remedieId) {
        this.remedieId = remedieId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Remedie getRemedie() {
        return remedie;
    }

    public void setRemedie(Remedie remedies) {
        this.remedie = remedies;
    }
}