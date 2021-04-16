package com.anas.mymall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.anas.mymall.DeliveryActivity.SELECTED_ADDRESS;

public class MyAdressesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView addressItemRecyclerView;
    private static AddressAdapter adapter;
    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adresses);
        deliverHereBtn = findViewById(R.id.myaddress_deliever_here_btn);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MY ADDRESS");

        addressItemRecyclerView = findViewById(R.id.address_recyclerView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        addressItemRecyclerView.setLayoutManager(linearLayout);
        List<AddressModel> addressItemModelList = new ArrayList<>();
        addressItemModelList.add(new AddressModel("Anas Abd Elazim ", "Elmahalla Elkobra mubark", "15612", true));
        addressItemModelList.add(new AddressModel("Anas Abd Elazim ", "Elmahalla Elkobra mubark", "15612", false));


        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECTED_ADDRESS) {
            deliverHereBtn.setVisibility(View.VISIBLE);
        } else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        adapter = new AddressAdapter(addressItemModelList, mode);
        addressItemRecyclerView.setAdapter(adapter);
        //  (SimpleItemAnimator) addressItemRecyclerView.getItemAnimator().animateAppearance()
        adapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect, int select) {
        adapter.notifyItemChanged(deselect);
        adapter.notifyItemChanged(select);

    }
}
