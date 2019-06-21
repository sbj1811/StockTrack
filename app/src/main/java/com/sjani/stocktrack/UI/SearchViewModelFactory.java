package com.sjani.stocktrack.UI;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sjani.stocktrack.Utils.DataRepository;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private DataRepository repository;
    private String apiKey;
    private String symbol;

    public SearchViewModelFactory(DataRepository repository, String apiKey, String symbol) {
        this.repository = repository;
        this.apiKey = apiKey;
        this.symbol = symbol;
    }

    public SearchViewModelFactory(DataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchListViewModel(repository,apiKey,symbol);
    }
}
