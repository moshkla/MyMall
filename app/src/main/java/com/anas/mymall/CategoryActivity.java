package com.anas.mymall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static com.anas.mymall.DatabaseQueries.lists;
import static com.anas.mymall.DatabaseQueries.loadCategoriesNames;
import static com.anas.mymall.DatabaseQueries.loadFragmentData;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categryRecyclerView;
    private MyMallPageAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);

        categryRecyclerView = findViewById(R.id.category_recyclerview);


        //----------- Mall RecyclerView Testing --------------
        LinearLayoutManager layoutTesting = new LinearLayoutManager(this);
        layoutTesting.setOrientation(RecyclerView.VERTICAL);
        categryRecyclerView.setLayoutManager(layoutTesting);
        //List<MyMallPageModel> myMallPageModels = new ArrayList<>();



        int listPosition=0;
        for (int x = 0; x < loadCategoriesNames.size(); x++) {
            if (loadCategoriesNames.get(x).equals(title)) {
                listPosition = x;
            }
        }
        if (listPosition==0){
            loadCategoriesNames.add(title);
            lists.add(new ArrayList<MyMallPageModel>());
            adapter = new MyMallPageAdapter(lists.get(loadCategoriesNames.size()-1));
            loadFragmentData(adapter,CategoryActivity.this,loadCategoriesNames.size()-1,title);

        }else {
            adapter = new MyMallPageAdapter(lists.get(listPosition));

        }


        categryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_search_icon:

                break;
            case R.id.home:
                finish();
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
