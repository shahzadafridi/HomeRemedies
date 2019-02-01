package com.example.opriday.homeremedies.Model;

/**
 * Created by Opriday on 12/23/2018.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Remedies {

    @SerializedName("remedies")
    @Expose
    private List<Remedy> remedies = null;

    public List<Remedy> getRemedies() {
        return remedies;
    }

    public void setRemedies(List<Remedy> remedies) {
        this.remedies = remedies;
    }

}