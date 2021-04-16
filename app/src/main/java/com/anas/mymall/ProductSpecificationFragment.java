package com.anas.mymall;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {

    public   List<ProductSpecificationModel> productSpecificationModelList;
    private RecyclerView productSpecificationRecyclerView;
    public static ProductSpecificationAdapter specificationAdapter;
    public ProductSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_specification, container, false);
        productSpecificationRecyclerView=view.findViewById(R.id.product_specification_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        productSpecificationRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);




        specificationAdapter=new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(specificationAdapter);
        specificationAdapter.notifyDataSetChanged();
        return view;
    }

}
