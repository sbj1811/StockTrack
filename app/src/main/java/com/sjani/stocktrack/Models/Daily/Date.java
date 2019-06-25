
package com.sjani.stocktrack.Models.Daily;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Date implements Serializable
{

    @SerializedName("0. date")
    @Expose
    private String _0Date;
    @SerializedName("1. open")
    @Expose
    private String _1Open;
    @SerializedName("2. high")
    @Expose
    private String _2High;
    @SerializedName("3. low")
    @Expose
    private String _3Low;
    @SerializedName("4. close")
    @Expose
    private String _4Close;
    @SerializedName("5. adjusted close")
    @Expose
    private String _5AdjustedClose;
    @SerializedName("6. volume")
    @Expose
    private String _6Volume;
    @SerializedName("7. dividend amount")
    @Expose
    private String _7DividendAmount;
    @SerializedName("8. split coefficient")
    @Expose
    private String _8SplitCoefficient;
    private final static long serialVersionUID = -5465397792985012262L;

    public String get_0Date() {
        return _0Date;
    }

    public void set_0Date(String _0Date) {
        this._0Date = _0Date;
    }

    public String get1Open() {
        return _1Open;
    }

    public void set1Open(String _1Open) {
        this._1Open = _1Open;
    }

    public String get2High() {
        return _2High;
    }

    public void set2High(String _2High) {
        this._2High = _2High;
    }

    public String get3Low() {
        return _3Low;
    }

    public void set3Low(String _3Low) {
        this._3Low = _3Low;
    }

    public String get4Close() {
        return _4Close;
    }

    public void set4Close(String _4Close) {
        this._4Close = _4Close;
    }

    public String get5AdjustedClose() {
        return _5AdjustedClose;
    }

    public void set5AdjustedClose(String _5AdjustedClose) {
        this._5AdjustedClose = _5AdjustedClose;
    }

    public String get6Volume() {
        return _6Volume;
    }

    public void set6Volume(String _6Volume) {
        this._6Volume = _6Volume;
    }

    public String get7DividendAmount() {
        return _7DividendAmount;
    }

    public void set7DividendAmount(String _7DividendAmount) {
        this._7DividendAmount = _7DividendAmount;
    }

    public String get8SplitCoefficient() {
        return _8SplitCoefficient;
    }

    public void set8SplitCoefficient(String _8SplitCoefficient) {
        this._8SplitCoefficient = _8SplitCoefficient;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_1Open", _1Open).append("_2High", _2High).append("_3Low", _3Low).append("_4Close", _4Close).append("_5AdjustedClose", _5AdjustedClose).append("_6Volume", _6Volume).append("_7DividendAmount", _7DividendAmount).append("_8SplitCoefficient", _8SplitCoefficient).toString();
    }

}
