package com.sjani.stocktrack.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjani.stocktrack.Utils.ListItemClickListener;
import com.sjani.stocktrack.Models.Search.BestMatch;
import com.sjani.stocktrack.Models.Search.SearchResult;
import com.sjani.stocktrack.R;
import com.sjani.stocktrack.Utils.MainListItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ListViewHolder> {

    MainListItemClickListener listener;
    SearchResult searchResult;

    public SearchAdapter(MainListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        BestMatch match = searchResult.getBestMatches().get(position);
        String name = match.get2Name();
        String symbol =  match.get1Symbol();
        holder.symbolTV.setText(symbol);
        holder.nameTV.setText(name);
    }

    @Override
    public int getItemCount() {
        if (searchResult == null) {
            return 0;
        }
        return searchResult.getBestMatches().size();
    }

    public void swapResults (SearchResult result) {
        if (searchResult == result) {
            return;
        }
        this.searchResult = result;
        if (result != null) {
            this.notifyDataSetChanged();
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.symbol)
        TextView symbolTV;

        @BindView(R.id.name)
        TextView nameTV;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            BestMatch match = searchResult.getBestMatches().get(getAdapterPosition());
            String name = match.get2Name();
            String symbol =  match.get1Symbol();
            listener.onItemClick(symbol,name);
        }
    }

}
