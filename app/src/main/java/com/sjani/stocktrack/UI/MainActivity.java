package com.sjani.stocktrack.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sjani.stocktrack.R;
import com.sjani.stocktrack.UI.Details.StockDetail;
import com.sjani.stocktrack.UI.Details.StockDetailFragment;
import com.sjani.stocktrack.Utils.CustomGestureListener;
import com.sjani.stocktrack.Utils.DataRepository;
import com.sjani.stocktrack.Utils.FactoryUtils;
import com.sjani.stocktrack.Utils.ListItemClickListener;
import com.sjani.stocktrack.Utils.SwipeController;
import com.sjani.stocktrack.Utils.SwipeControllerActions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SYMBOL = "symbol";

    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    LinearLayoutManager layoutManager;
    MainListAdapter adapter;
    SearchListViewModel viewModel;
    SwipeController swipeController;
    private boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        adapter = new MainListAdapter(this,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        SearchViewModelFactory factory = FactoryUtils.getFactory(this);
        viewModel = ViewModelProviders.of(this,factory).get(SearchListViewModel.class);
        viewModel.getAllQuotes().observe(this, QuoteList -> {
            adapter.swapQuotes(QuoteList);
            recyclerView.setVisibility(View.VISIBLE);
            viewModel.setupPeriodicRefreshWork();
            swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                    viewModel.removeQuotefromDb(adapter.globalQuoteList.get(position).get01Symbol());
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                }
            });

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
            itemTouchhelper.attachToRecyclerView(recyclerView);

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeController.onDraw(c);
                }
            });
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchRequested();
            }
        });
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String symbol) {
        if (mDualPane){
            StockDetailFragment stockDetailFragment = StockDetailFragment.newInstance(symbol);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container,stockDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StockDetail.class);
            intent.putExtra(SYMBOL,symbol);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                startActivity(intent);
                this.overridePendingTransition(R.xml.slide_from_right,R.xml.slide_from_left);
            } else {
                startActivity(intent);
            }
        }
    }
}
