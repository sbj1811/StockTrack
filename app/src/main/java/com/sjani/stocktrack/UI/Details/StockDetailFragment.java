package com.sjani.stocktrack.UI.Details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.sjani.stocktrack.UI.SearchListViewModel;
import com.sjani.stocktrack.UI.SearchViewModelFactory;
import com.sjani.stocktrack.Utils.FactoryUtils;
import com.anychart.data.Set;
import com.anychart.*;
import com.sjani.stocktrack.Utils.NumberUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockDetailFragment extends Fragment {

    private static final String TAG = StockDetailFragment.class.getSimpleName();
    private static final String SYMBOL = "param1";

    private String symbol;
    private Cartesian cartesian;
    SearchListViewModel viewModel;
    @BindView(R.id.detail_symbol)
    TextView symbolTV;
    @BindView(R.id.detail_name)
    TextView nameTV;
    @BindView(R.id.detail_price)
    TextView priceTV;
    @BindView(R.id.detail_change)
    TextView changeTV;
    @BindView(R.id.detail_high_price)
    TextView highTV;
    @BindView(R.id.detail_low_price)
    TextView lowTV;
    @BindView(R.id.detail_volume_count)
    TextView volumeTV;
    @BindView(R.id.detail_open_price)
    TextView openTV;
    @BindView(R.id.any_chart_view)
    AnyChartView anyChartView;


    public StockDetailFragment() {
        // Required empty public constructor
    }

    public static StockDetailFragment newInstance(String symbol) {
        StockDetailFragment fragment = new StockDetailFragment();
        Bundle args = new Bundle();
        args.putString(SYMBOL, symbol);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            symbol = getArguments().getString(SYMBOL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        SearchViewModelFactory factory = FactoryUtils.getFactory(getContext());
        viewModel = ViewModelProviders.of(this,factory).get(SearchListViewModel.class);
        viewModel.getQuote(symbol).observe(this, Quote -> {
            createView(Quote);
        });
    }

    private void createView(GlobalQuote Quote) {
        symbolTV.setText(Quote.get01Symbol());
        nameTV.setText(Quote.get_11Name());
        Double pr = NumberUtil.getDoublePrice(Quote.get10ChangePercent());
        String changeVal = NumberUtil.formatPercent(pr);
        if(pr<0.0){
            changeTV.setBackgroundColor(getContext().getResources().getColor(R.color.red));
        } else {
            changeTV.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        String price = NumberUtil.formatPrice(Quote.get05Price());
        String high = NumberUtil.formatPrice(Quote.get03High());
        String low = NumberUtil.formatPrice(Quote.get04Low());
        String open = NumberUtil.formatPrice(Quote.get02Open());
        String vol = NumberUtil.truncateNumber(Quote.get06Volume());
        priceTV.setText(price);
        openTV.setText(open);
        highTV.setText(high);
        lowTV.setText(low);
        volumeTV.setText(vol);
        changeTV.setText(changeVal);
        createChart(Quote.get01Symbol());
    }

    private void createChart(String symbol){
        cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("1986", 3.6));
        seriesData.add(new CustomDataEntry("1987", 7.1));
        seriesData.add(new CustomDataEntry("1988", 8.5));
        seriesData.add(new CustomDataEntry("1989", 9.2));
        seriesData.add(new CustomDataEntry("1990", 10.1));
        seriesData.add(new CustomDataEntry("1991", 11.6));
        seriesData.add(new CustomDataEntry("1992", 16.4));
        seriesData.add(new CustomDataEntry("1993", 18.0));
        seriesData.add(new CustomDataEntry("1994", 13.2));
        seriesData.add(new CustomDataEntry("1995", 12.0));
        seriesData.add(new CustomDataEntry("1996", 3.2));
        seriesData.add(new CustomDataEntry("1997", 4.1));
        seriesData.add(new CustomDataEntry("1998", 6.3));
        seriesData.add(new CustomDataEntry("1999", 9.4));
        seriesData.add(new CustomDataEntry("2000", 11.5));
        seriesData.add(new CustomDataEntry("2001", 13.5));
        seriesData.add(new CustomDataEntry("2002", 14.8));
        seriesData.add(new CustomDataEntry("2003", 16.6));
        seriesData.add(new CustomDataEntry("2004", 18.1));
        seriesData.add(new CustomDataEntry("2005", 17.0));
        seriesData.add(new CustomDataEntry("2006", 16.6));
        seriesData.add(new CustomDataEntry("2007", 14.1));
        seriesData.add(new CustomDataEntry("2008", 15.7));
        seriesData.add(new CustomDataEntry("2009", 12.09));
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");


        Line series1 = cartesian.line(series1Mapping);
        series1.name(symbol);
        series1.stroke("#0A995B");
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);

    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

    }

}


