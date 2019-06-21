
package com.sjani.stocktrack.Models.Search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class SearchResult {

    @SerializedName("bestMatches")
    @Expose
    private List<BestMatch> bestMatches = null;

    public List<BestMatch> getBestMatches() {
        return bestMatches;
    }

    public void setBestMatches(List<BestMatch> bestMatches) {
        this.bestMatches = bestMatches;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("bestMatches", bestMatches).toString();
    }

}
