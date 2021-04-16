package com.anas.mymall.ui.myMall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anas.mymall.MainActivity;
import com.anas.mymall.MyMallPageAdapter;
import com.anas.mymall.MyMallPageModel;
import com.anas.mymall.R;
import com.anas.mymall.CategoryAdapter;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

import static com.anas.mymall.DatabaseQueries.categoryModelsList;
import static com.anas.mymall.DatabaseQueries.firebaseFirestore;
import static com.anas.mymall.DatabaseQueries.lists;
import static com.anas.mymall.DatabaseQueries.loadCategories;
import static com.anas.mymall.DatabaseQueries.loadCategoriesNames;
import static com.anas.mymall.DatabaseQueries.loadFragmentData;
import static com.anas.mymall.MyMallPageAdapter.StripAdViewHolder.stripImage;

public class MyMallFragment extends Fragment {

    private MyMallViewModel myMallViewModel;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    private MyMallPageAdapter adapter;

    public static SwipeRefreshLayout swipeRefreshLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        myMallViewModel =
                ViewModelProviders.of(this).get(MyMallViewModel.class);
        View view = inflater.inflate(R.layout.fragment_mymall, container, false);


        MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        swipeRefreshLayout=view.findViewById(R.id.refresh_layout);

        categoryRecyclerView = view.findViewById(R.id.mall_category_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        firebaseFirestore = FirebaseFirestore.getInstance();

        categoryAdapter = new CategoryAdapter(categoryModelsList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        if (categoryModelsList.size() ==0){
            loadCategories(categoryAdapter,getContext());
        }else {
            categoryAdapter.notifyDataSetChanged();
        }



        //----------- Mall RecyclerView Testing --------------
        RecyclerView mallTesting = view.findViewById(R.id.mall_testing_recyclerview);
        LinearLayoutManager layoutTesting = new LinearLayoutManager(getActivity());
        layoutTesting.setOrientation(RecyclerView.VERTICAL);
        mallTesting.setLayoutManager(layoutTesting);


        if (lists.size() ==0){
            loadCategoriesNames.add("Home");
            lists.add(new ArrayList<MyMallPageModel>());
            adapter = new MyMallPageAdapter(lists.get(0));
            loadFragmentData(adapter,getContext(),0,"Home");

        }else {
            adapter=new MyMallPageAdapter(lists.get(0));
            adapter.notifyDataSetChanged();
        }
        mallTesting.setAdapter(adapter);

        //----------- Mall RecyclerView Testing --------------
        //----------- Refresh Layout --------------
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                Glide.with(getContext()).load(R.mipmap.ad_photo).into(stripImage);

                swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary));
                swipeRefreshLayout.setRefreshing(true);
                categoryModelsList.clear();
                lists.clear();
                loadCategoriesNames.clear();

                loadCategories(categoryAdapter,getContext());

                loadCategoriesNames.add("Home");
                lists.add(new ArrayList<MyMallPageModel>());
                loadFragmentData(adapter,getContext(),0,"Home");
            }
        });
        //----------- Refresh Layout --------------


        return view;
    }


}