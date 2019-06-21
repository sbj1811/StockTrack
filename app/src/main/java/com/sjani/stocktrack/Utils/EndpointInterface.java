package com.sjani.stocktrack.Utils;

import com.sjani.stocktrack.Models.Quote.SymbolQuote;
import com.sjani.stocktrack.Models.Search.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndpointInterface {

    String API_ENDPOINT = "https://www.alphavantage.co";

    @GET("/query")
    Call<SearchResult> getSymbols(@Query("function") String function, @Query("keywords") String symbol, @Query("apikey") String apiKey);

    @GET("/query")
    Call<SymbolQuote> getSymbolQuote(@Query("function") String function, @Query("symbol") String symbol, @Query("apikey") String apiKey);

}
