package com.sjani.stocktrack.Utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sjani.stocktrack.Data.QuoteDao;
import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.Models.Quote.SymbolQuote;
import com.sjani.stocktrack.Models.Search.SearchResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    public MutableLiveData<SearchResult> searchSymbols(String apiKey, String keyword){
        MutableLiveData<SearchResult> resultLiveData = new MutableLiveData<>();
        StockApiConnection.getApi().getSymbols("SYMBOL_SEARCH",keyword,apiKey).enqueue(new Callback<SearchResult>() {

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

    public LiveData<List<GlobalQuote>> getAllQuotesFromDb(){
        return quoteDao.getAllQuotes();
    }

    public LiveData<GlobalQuote> getQuoteFromDb(String symbol){
        return quoteDao.getSymbolQuote(symbol);
    }

    public void removeQuote(String symbol){
        Observable.fromCallable(() -> {
            quoteDao.deleteSymbolQuote(symbol);
            return false;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void saveSymbolQuote(String apiKey, String symbol, String name){
        MutableLiveData<SearchResult> resultLiveData = new MutableLiveData<>();
        StockApiConnection.getApi().getSymbolQuote("GLOBAL_QUOTE",symbol,apiKey).enqueue(new Callback<SymbolQuote>() {
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



}
