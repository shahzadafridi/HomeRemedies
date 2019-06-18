package com.example.opriday.homeremedies.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemedieData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Remedie> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Remedie> getData() {
        return data;
    }

    public void setData(List<Remedie> data) {
        this.data = data;
    }
}
