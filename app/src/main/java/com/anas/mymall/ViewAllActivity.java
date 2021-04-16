package com.anas.mymall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;
    private Toolbar toolbar;
    public static List<WishListModel> wishListModelList;
    public static List<HorizontalProductModel> horizontalProductModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        viewAllRecyclerView = findViewById(R.id.view_all_recylerView);
        viewAllGridView = findViewById(R.id.view_all_gridView);

        if (layout_code == 0) {
            viewAllGridView.setVisibility(View.GONE);
            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            viewAllRecyclerView.setLayoutManager(layoutManager);
           // List<WishListModel> viewAllList = new ArrayList<>();

            WishListAdapter adapter = new WishListAdapter(wishListModelList, false);
            viewAllRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else if (layout_code == 1) {
            viewAllRecyclerView.setVisibility(View.GONE);
            viewAllGridView.setVisibility(View.VISIBLE);


            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductModelList);
            viewAllGridView.setAdapter(gridProductLayoutAdapter);
        }
    }
}
