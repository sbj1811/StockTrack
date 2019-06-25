package com.sjani.stocktrack.Utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sjani.stocktrack.Data.QuoteDao;
import com.sjani.stocktrack.Models.Daily.Date;
import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.Models.Quote.SymbolQuote;
import com.sjani.stocktrack.Models.Search.SearchResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private QuoteDao quoteDao;

    public DataRepository(QuoteDao quoteDao) {
        this.quoteDao = quoteDao;
    }

    public DataRepository() {
    }

    public MutableLiveData<SearchResult> searchSymbols(String apiKey, String keyword) {
        MutableLiveData<SearchResult> resultLiveData = new MutableLiveData<>();
        StockApiConnection.getApi().getSymbols("SYMBOL_SEARCH", keyword, apiKey).enqueue(new Callback<SearchResult>() {

            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                resultLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return resultLiveData;
    }

    public LiveData<List<GlobalQuote>> getAllQuotesFromDb() {
        return quoteDao.getAllQuotes();
    }

    public LiveData<GlobalQuote> getQuoteFromDb(String symbol) {
        return quoteDao.getSymbolQuote(symbol);
    }

    public void removeQuote(String symbol) {
        Observable.fromCallable(() -> {
            quoteDao.deleteSymbolQuote(symbol);
            return false;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void saveSymbolQuote(String apiKey, String symbol, String name) {
        MutableLiveData<SearchResult> resultLiveData = new MutableLiveData<>();
        StockApiConnection.getApi().getSymbolQuote("GLOBAL_QUOTE", symbol, apiKey).enqueue(new Callback<SymbolQuote>() {
            @Override
            public void onResponse(Call<SymbolQuote> call, Response<SymbolQuote> response) {
                GlobalQuote quote = response.body().getGlobalQuote();
                quote.set_11Name(name);
                Observable.fromCallable(() -> {
                    quoteDao.save(quote);
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }

            @Override
            public void onFailure(Call<SymbolQuote> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Date>> getSymbolData(String apiKey, String symbol, String function) {
        MutableLiveData<List<Date>> dailyPriceLiveData = new MutableLiveData<>();
        List<Date> dateList = new ArrayList<>();
        String interval = "5min";
        if (function.equals("TIME_SERIES_INTRADAY")) {
            StockApiConnection.getApi().getDailySeriesIntra(function, symbol, apiKey, interval).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        //                JSONObject meta = jsonObject.getJSONObject("Meta Data");
                        JSONObject series = jsonObject.getJSONObject("Time Series (5min)");

                        for (int i = 0; i < series.names().length(); i++) {
                            JSONObject object = series.getJSONObject(series.names().getString(i));
                            Date date = new Date();
                            date.set_0Date(NumberUtils.convertTime(series.names().getString(i).split(" ")[1]));
                            date.set1Open(object.getString("1. open"));
                            date.set2High(object.getString("2. high"));
                            date.set3Low(object.getString("3. low"));
                            date.set4Close(object.getString("4. close"));
                            date.set6Volume(object.getString("5. volume"));
                            dateList.add(date);
                        }
                        dailyPriceLiveData.setValue(dateList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } else {
            StockApiConnection.getApi().getDailySeries(function, symbol, apiKey).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        //                JSONObject meta = jsonObject.getJSONObject("Meta Data");
                        JSONObject series;
                        if (function.equals("TIME_SERIES_DAILY")) {
                            series = jsonObject.getJSONObject("Time Series (Daily)");
                        } else if (function.equals("TIME_SERIES_MONTHLY")) {
                            series = jsonObject.getJSONObject("Monthly Time Series");
                        } else {
                            series = jsonObject.getJSONObject("Time Series (Daily)");
                        }


                        for (int i = 0; i < series.names().length(); i++) {
                            JSONObject object = series.getJSONObject(series.names().getString(i));
                            Date date = new Date();
                            date.set_0Date(series.names().getString(i));
                            date.set1Open(object.getString("1. open"));
                            date.set2High(object.getString("2. high"));
                            date.set3Low(object.getString("3. low"));
                            date.set4Close(object.getString("4. close"));
                            date.set6Volume(object.getString("5. volume"));
                            dateList.add(date);
                        }
                        dailyPriceLiveData.setValue(dateList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        return dailyPriceLiveData;
    }

}
