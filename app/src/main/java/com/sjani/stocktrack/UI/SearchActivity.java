package com.sjani.stocktrack.UI;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.sjani.stocktrack.Utils.DataRepository;
import com.sjani.stocktrack.Utils.FactoryUtils;
import com.sjani.stocktrack.Utils.ListItemClickListener;
import com.sjani.stocktrack.R;
import com.sjani.stocktrack.Utils.MainListItemClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;


import io.reactivex.Observable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements MainListItemClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.rv_search)
    RecyclerView recyclerView;
    SearchAdapter adapter;
    SearchListViewModel viewModel;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        handleSearch(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch(intent);
    }
    private void handleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,SuggestionProvider.AUTHORITY,SuggestionProvider.MODE);
            suggestions.saveRecentQuery(searchQuery,null);
            adapter = new SearchAdapter(this);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            SearchViewModelFactory factory = FactoryUtils.getFactory(this,getString(R.string.api_key),searchQuery);
            viewModel = ViewModelProviders.of(this,factory).get(SearchListViewModel.class);
            viewModel.getSearchResults().observe(this, SearchResult -> {
                adapter.swapResults(SearchResult);
                recyclerView.setVisibility(View.VISIBLE);
            });

        }else if(Intent.ACTION_VIEW.equals(intent.getAction())) {
            String selectedSuggestionRowId =  intent.getDataString();
            //execution comes here when an item is selected from search suggestions
            //you can continue from here with user selected search item
            Toast.makeText(this, "selected search suggestion "+selectedSuggestionRowId,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(String symbol,String name) {
            viewModel.setQuote(symbol,name);
            finish();
    }
}
