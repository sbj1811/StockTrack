package com.sjani.stocktrack.UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjani.stocktrack.Models.Quote.GlobalQuote;
import com.sjani.stocktrack.R;
import com.sjani.stocktrack.Utils.ListItemClickListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder> {

    private static final String TAG = MainListAdapter.class.getSimpleName();

    ListItemClickListener listener;
    List<GlobalQuote> globalQuoteList;
    Context context;

    public MainListAdapter(ListItemClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item,parent,false);
        return new MainListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListViewHolder holder, int position) {
        GlobalQuote quote = globalQuoteList.get(position);
        holder.symbolTV.setText(quote.get01Symbol());
        holder.nameTV.setText(quote.get_11Name());
        String[] change = quote.get10ChangePercent().split("%");
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        String price = df.format(Double.parseDouble(quote.get05Price()));
        Double pr = Double.parseDouble(change[0]);
        String changeVal = df.format(pr)+"%";
        if(pr<0.0){
            holder.changeTV.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            holder.changeTV.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.priceTV.setText(price);
        holder.changeTV.setText(changeVal);
    }

    @Override
    public int getItemCount() {
        if(globalQuoteList == null || globalQuoteList.size()==0)
        return 0;
        return globalQuoteList.size();
    }

    public void swapQuotes(List<GlobalQuote> quoteList) {
        if(globalQuoteList==quoteList) return;
        this.globalQuoteList = quoteList;
        if(quoteList != null){
            this.notifyDataSetChanged();
        }
    }

    public class MainListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.company_symbol)
        TextView symbolTV;
        @BindView(R.id.company_name)
        TextView nameTV;
        @BindView(R.id.company_price)
        TextView priceTV;
        @BindView(R.id.company_change)
        TextView changeTV;

        public MainListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            GlobalQuote quote = globalQuoteList.get(getAdapterPosition());
            String symbol = quote.get01Symbol();
            listener.onItemClick(symbol);
        }
    }
}
