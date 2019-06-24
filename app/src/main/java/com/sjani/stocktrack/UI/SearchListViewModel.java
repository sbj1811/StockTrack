package com.sjani.stocktrack.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.Models.Search.SearchResult;
import com.sjani.stocktrack.Utils.DataRepository;
import com.sjani.stocktrack.Utils.RefreshScheduler;

import java.util.List;

public class SearchListViewModel extends ViewModel {

    private static final String TAG = SearchListViewModel.class.getSimpleName();

    private DataRepository repository;
    String apiKey;
    String symbol;
    private MutableLiveData<SearchResult> searchResultMutableLiveData;

    private LiveData<List<GlobalQuote>> quotesLiveData;
    private LiveData<GlobalQuote> quoteLiveData;

    public SearchListViewModel(DataRepository repository, String apiKey, String symbol) {
        this.repository = repository;
        this.apiKey = apiKey;
        this.symbol = symbol;
        searchResultMutableLiveData = this.repository.searchSymbols(apiKey, symbol);

    }

    public LiveData<SearchResult> getSearchResults() {
        return searchResultMutableLiveData;
    }

    public LiveData<List<GlobalQuote>> getAllQuotes() {
        quotesLiveData = this.repository.getAllQuotesFromDb();
        return quotesLiveData;
    }

    public LiveData<GlobalQuote> getQuote(String symbol) {
        quoteLiveData = this.repository.getQuoteFromDb(symbol);
        return quoteLiveData;
    }

    public void removeQuotefromDb(String symbol){
        this.repository.removeQuote(symbol);
    }

    public void setQuote(String symbol, String name) {
        this.repository.saveSymbolQuote(apiKey, symbol, name);
    }

    public void setupPeriodicRefreshWork(){
        RefreshScheduler.refreshPeriodicWork();
    }
}
