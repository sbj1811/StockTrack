package com.sjani.stocktrack.UI.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sjani.stocktrack.R;

public class StockDetail extends AppCompatActivity {

    private static final String SYMBOL = "symbol";

    private String symbol;
    private StockDetailFragment stockDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        if(savedInstanceState==null){
            Intent intent = getIntent();
            symbol = intent.getExtras().getString(SYMBOL);
        } else {
            symbol = savedInstanceState.getString(SYMBOL);
        }


        if(savedInstanceState==null){
            stockDetailFragment = (StockDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_container);
            if(stockDetailFragment==null){
                stockDetailFragment = StockDetailFragment.newInstance(symbol);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container,stockDetailFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SYMBOL, symbol);
        super.onSaveInstanceState(outState);
    }
}
