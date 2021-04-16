package com.anas.mymall;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    public static final int SELECTED_ADDRESS = 0;

    private Toolbar toolbar;
    private RecyclerView cartItemRecyclerView;
    private Button addOrRemoveAddress;
    private TextView  total_cart_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delivery");


        total_cart_amount=findViewById(R.id.delivery_total_amount);
        cartItemRecyclerView=findViewById(R.id.delivery_recyclerView);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        cartItemRecyclerView.setLayoutManager(linearLayout);
        List<CartItemModel> cartItemModelList=new ArrayList<>();

        cartItemModelList.add(new CartItemModel(1,"Price (3 Items) ","LE:39000","LE:39000","FREE","LE:3000"));
        CartAdapter adapter=new CartAdapter(cartItemModelList,total_cart_amount);
        cartItemRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addOrRemoveAddress=findViewById(R.id.add_or_remove_address_btn);
        addOrRemoveAddress.setVisibility(View.VISIBLE);
        addOrRemoveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAddressIntent = new Intent(DeliveryActivity.this, MyAdressesActivity.class);
                MyAddressIntent.putExtra("MODE",SELECTED_ADDRESS);
                startActivity(MyAddressIntent);
                finish();
            }
        });

    }
}
