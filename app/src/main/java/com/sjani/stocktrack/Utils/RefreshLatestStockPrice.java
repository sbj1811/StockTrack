package com.sjani.stocktrack.Utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.sjani.stocktrack.Data.QuoteDatabase;
import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.R;
import com.sjani.stocktrack.UI.MainActivity;

import java.util.List;

public class RefreshLatestStockPrice extends Worker {

    private static final String TAG = RefreshLatestStockPrice.class.getSimpleName();

    public RefreshLatestStockPrice(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        Log.e(TAG, "doWork: HERE ");
        QuoteDatabase quoteDatabase = QuoteDatabase.getInstance(context.getApplicationContext());
        DataRepository repository =  new DataRepository(quoteDatabase.quoteDao());
        List<GlobalQuote> globalQuoteList = repository.getAllQuotesFromDb().getValue();
        if(globalQuoteList!=null && globalQuoteList.size()!=0){
            for(GlobalQuote globalQuote: globalQuoteList){
                repository.saveSymbolQuote(context.getResources().getString(R.string.api_key),globalQuote.get01Symbol(),globalQuote.get_11Name());
            }
        }

        return Result.success();
    }
}
