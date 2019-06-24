package com.sjani.stocktrack.Utils;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockApiConnection {

    private static final String TAG = StockApiConnection.class.getSimpleName();

    public static EndpointInterface getApi(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndpointInterface.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(EndpointInterface.class);

    }
}
