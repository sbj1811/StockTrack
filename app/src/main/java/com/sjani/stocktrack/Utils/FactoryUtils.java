package com.sjani.stocktrack.Utils;

import android.content.Context;

import com.sjani.stocktrack.Data.QuoteDatabase;
import com.sjani.stocktrack.UI.SearchViewModelFactory;

public class FactoryUtils {
    public static SearchViewModelFactory getFactory(Context context, String apiKey, String symbol){
        QuoteDatabase quoteDatabase = QuoteDatabase.getInstance(context.getApplicationContext());
        return new SearchViewModelFactory(new DataRepository(quoteDatabase.quoteDao()),apiKey,symbol);
    }
    public static SearchViewModelFactory getFactory(Context context){
        QuoteDatabase quoteDatabase = QuoteDatabase.getInstance(context.getApplicationContext());
        return new SearchViewModelFactory(new DataRepository(quoteDatabase.quoteDao()));
    }
}
