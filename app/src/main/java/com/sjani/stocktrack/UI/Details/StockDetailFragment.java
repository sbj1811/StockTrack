package com.sjani.stocktrack.UI.Details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.sjani.stocktrack.Models.Daily.Date;
import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.sjani.stocktrack.UI.SearchListViewModel;
import com.sjani.stocktrack.UI.SearchViewModelFactory;
import com.sjani.stocktrack.Utils.FactoryUtils;
import com.anychart.data.Set;
import com.anychart.*;
import com.sjani.stocktrack.Utils.NumberUtils;

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

    @BindView(R.id.detail_daily)
    Button dayButton;
    @BindView(R.id.detail_weekly)
    Button weeklyButton;
    @BindView(R.id.detail_month)
    Button monthButton;
    @BindView(R.id.detail_three_month)
    Button threemonthButton;
    @BindView(R.id.detail_one_year)
    Button oneyearButton;


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
        SearchViewModelFactory factory = FactoryUtils.getFactory(getContext(),getString(R.string.api_key),symbol);
        viewModel = ViewModelProviders.of(this,factory).get(SearchListViewModel.class);
        viewModel.getQuote(symbol).observe(this, Quote -> {
            createView(Quote);

        });

    }

    private void createView(GlobalQuote Quote) {
        symbolTV.setText(Quote.get01Symbol());
        nameTV.setText(Quote.get_11Name());
        Double pr = NumberUtils.getDoublePrice(Quote.get10ChangePercent());
        String changeVal = NumberUtils.formatPercent(pr);
        if(pr<0.0){
            changeTV.setBackgroundColor(getContext().getResources().getColor(R.color.red));
        } else {
            changeTV.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        String price = NumberUtils.formatPrice(Quote.get05Price());
        String high = NumberUtils.formatPrice(Quote.get03High());
        String low = NumberUtils.formatPrice(Quote.get04Low());
        String open = NumberUtils.formatPrice(Quote.get02Open());
        String vol = NumberUtils.truncateNumber(Quote.get06Volume());
        priceTV.setText(price);
        openTV.setText(open);
        highTV.setText(high);
        lowTV.setText(low);
        volumeTV.setText(vol);
        changeTV.setText(changeVal);
        viewModel.getDailySymbolData(symbol,getContext().getString(R.string.function_intraday)).observe(this, DateList ->{
            createChart(Quote.get01Symbol(),DateList,getContext().getString(R.string.daily));
        });
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDailySymbolData(symbol,getContext().getString(R.string.function_intraday)).observe(getActivity(), DateList ->{
                    createChart(Quote.get01Symbol(),DateList,getContext().getString(R.string.daily));
                });
            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDailySymbolData(symbol,getContext().getString(R.string.function_daily)).observe(getActivity(), DateList ->{
                    createChart(Quote.get01Symbol(),DateList,getContext().getString(R.string.daily_weekly));
                });
            }
        });
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDailySymbolData(symbol,getContext().getString(R.string.function_daily)).observe(getActivity(), DateList ->{
                    createChart(Quote.get01Symbol(),DateList,getContext().getString(R.string.daily_monthly));
                });
            }
        });
        threemonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDailySymbolData(symbol,getContext().getString(R.string.function_daily)).observe(getActivity(), DateList ->{
                    createChart(Quote.get01Symbol(),DateList,getContext().getString(R.string.daily_three_monthly));
                });
            }
        });
        oneyearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDailySymbolData(symbol,getContext().getString(R.string.function_monthly)).observe(getActivity(), DateList ->{
                    createChart(Quote.get01Symbol(),DateList,getContext().getString(R.string.monthly_one_year));
                });
            }
        });
    }

    private void createChart(String symbol, List<Date> dateList, String type){

        AnyChartView anyChartView = getActivity().findViewById(R.id.any_chart_view);

        Cartesian cartesian = AnyChart.line();
        cartesian.dispose();
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
        int offset = 93;
        switch (type) {
            case "daily":
                offset = 0;
                break;
            case "daily weekly":
                offset = 93;
                break;
            case "daily monthly":
                offset = 70;
                break;
            case "daily three monthly":
                offset = 15;
                break;
            case "monthly one year":
                offset = dateList.size()-12;
                break;
        }

        List<DataEntry> seriesData = new ArrayList<>();
        for (int i=0; i <dateList.size()-offset; i++){
            Date date = dateList.get(i);
            String price = NumberUtils.formatPrice(date.get2High());
            if(type.equals("daily")) {
                seriesData.add(new CustomDataEntry(date.get_0Date(), Double.parseDouble(price)));
            } else {
                seriesData.add(new CustomDataEntry(NumberUtils.convertDate(date.get_0Date(),type),Double.parseDouble(price)));
            }
        }
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


