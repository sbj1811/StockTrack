
package com.sjani.stocktrack.Models.Quote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class SymbolQuote {

    @SerializedName("Global Quote")
    @Expose
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

    public void setGlobalQuote(GlobalQuote globalQuote) {
        this.globalQuote = globalQuote;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("globalQuote", globalQuote).toString();
    }

}
