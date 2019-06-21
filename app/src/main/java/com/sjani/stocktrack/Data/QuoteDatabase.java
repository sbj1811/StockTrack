package com.sjani.stocktrack.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sjani.stocktrack.Models.Quote.GlobalQuote;

@Database(entities = {GlobalQuote.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class QuoteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "quote";
    private static final Object LOCK = new Object();
    private static QuoteDatabase sInstance;

    public static QuoteDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), QuoteDatabase.class, QuoteDatabase.DATABASE_NAME)
            .build();
            }
        }
        return sInstance;
        }


public abstract QuoteDao quoteDao();
}
