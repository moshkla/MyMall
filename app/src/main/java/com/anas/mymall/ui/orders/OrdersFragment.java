package com.anas.mymall.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.mymall.OrderAdapter;
import com.anas.mymall.OrderItemModel;
import com.anas.mymall.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private OrdersViewModel ordersViewModel;

    private RecyclerView orderRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        orderRecyclerView=view.findViewById(R.id.orders_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        orderRecyclerView.setLayoutManager(layoutManager);

        List<OrderItemModel> orderItemModelList=new ArrayList<>();
        orderItemModelList.add(new OrderItemModel(R.mipmap.phone_image,5,"IphoneX","Delivered on Mon 23th Jun 2020"));
        orderItemModelList.add(new OrderItemModel(R.mipmap.phone_image,4,"IphoneX","Cancelled"));
        orderItemModelList.add(new OrderItemModel(R.mipmap.phone_image,3,"IphoneX","Delivered on Mon 23th Jun 2020"));
        orderItemModelList.add(new OrderItemModel(R.mipmap.phone_image,2,"IphoneX","Delivered on Mon 23th Jun 2020"));

        OrderAdapter adapter=new OrderAdapter(orderItemModelList);
        orderRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}