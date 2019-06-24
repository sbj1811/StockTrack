
package com.sjani.stocktrack.Models.Quote;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity(tableName = "quote")
public class GlobalQuote {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "symbol")
    @SerializedName("01. symbol")
    @Expose
    private String _01Symbol;
    @ColumnInfo(name = "open")
    @SerializedName("02. open")
    @Expose
    private String _02Open;
    @ColumnInfo(name = "high")
    @SerializedName("03. high")
    @Expose
    private String _03High;
    @ColumnInfo(name = "low")
    @SerializedName("04. low")
    @Expose
    private String _04Low;
    @ColumnInfo(name = "price")
    @SerializedName("05. price")
    @Expose
    private String _05Price;
    @ColumnInfo(name = "volume")
    @SerializedName("06. volume")
    @Expose
    private String _06Volume;
    @SerializedName("07. latest trading day")
    @Expose
    private String _07LatestTradingDay;
    @SerializedName("08. previous close")
    @Expose
    private String _08PreviousClose;
    @SerializedName("09. change")
    @Expose
    @ColumnInfo(name = "change")
    private String _09Change;
    @SerializedName("10. change percent")
    @Expose
    private String _10ChangePercent;
    @ColumnInfo(name = "name")
    private String _11Name;

    public String get01Symbol() {
        return _01Symbol;
    }

    public void set01Symbol(String _01Symbol) {
        this._01Symbol = _01Symbol;
    }

    public String get02Open() {
        return _02Open;
    }

    public void set02Open(String _02Open) {
        this._02Open = _02Open;
    }

    public String get03High() {
        return _03High;
    }

    public void set03High(String _03High) {
        this._03High = _03High;
    }

    public String get04Low() {
        return _04Low;
    }

    public void set04Low(String _04Low) {
        this._04Low = _04Low;
    }

    public String get05Price() {
        return _05Price;
    }

    public void set05Price(String _05Price) {
        this._05Price = _05Price;
    }

    public String get06Volume() {
        return _06Volume;
    }

    public void set06Volume(String _06Volume) {
        this._06Volume = _06Volume;
    }

    public String get07LatestTradingDay() {
        return _07LatestTradingDay;
    }

    public void set07LatestTradingDay(String _07LatestTradingDay) {
        this._07LatestTradingDay = _07LatestTradingDay;
    }

    public String get08PreviousClose() {
        return _08PreviousClose;
    }

    public void set08PreviousClose(String _08PreviousClose) {
        this._08PreviousClose = _08PreviousClose;
    }

    public String get09Change() {
        return _09Change;
    }

    public void set09Change(String _09Change) {
        this._09Change = _09Change;
    }

    public String get10ChangePercent() {
        return _10ChangePercent;
    }

    public String get_11Name() {
        return _11Name;
    }

    public void set_11Name(String _11Name) {
        this._11Name = _11Name;
    }

    public void set10ChangePercent(String _10ChangePercent) {
        this._10ChangePercent = _10ChangePercent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_01Symbol", _01Symbol).append("_02Open", _02Open).append("_03High", _03High).append("_04Low", _04Low).append("_05Price", _05Price).append("_06Volume", _06Volume).append("_07LatestTradingDay", _07LatestTradingDay).append("_08PreviousClose", _08PreviousClose).append("_09Change", _09Change).append("_10ChangePercent", _10ChangePercent).append("_11Name", _11Name).toString();
    }

}
