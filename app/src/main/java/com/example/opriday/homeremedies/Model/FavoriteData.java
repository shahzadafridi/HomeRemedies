package com.example.opriday.homeremedies.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Favorite> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Favorite> getData() {
        return data;
    }

    public void setData(List<Favorite> data) {
        this.data = data;
    }
}
