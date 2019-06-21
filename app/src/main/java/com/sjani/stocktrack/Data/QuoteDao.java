package com.sjani.stocktrack.Data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sjani.stocktrack.Models.Quote.GlobalQuote;

import java.util.List;

@Dao
public interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(GlobalQuote globalQuote);

    @Query("SELECT * FROM quote")
    LiveData<List<GlobalQuote>> getAllQuotes();

    @Query("SELECT * FROM quote WHERE symbol = :symbol")
    LiveData<GlobalQuote> getSymbolQuote(String symbol);

    @Query("DELETE FROM quote WHERE symbol = :symbol")
    void deleteSymbolQuote(String symbol);

    @Query("DELETE FROM quote")
    void clearTable();
}
