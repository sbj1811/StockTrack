package com.sjani.stocktrack.UI.Details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjani.stocktrack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SYMBOL = "param1";

    // TODO: Rename and change types of parameters
    private String symbol;


    public StockDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param symbol Parameter 1.
     * @return A new instance of fragment StockDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
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


}
