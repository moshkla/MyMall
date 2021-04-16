package com.anas.mymall.ui.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.mymall.AddAddressActivity;
import com.anas.mymall.CartAdapter;
import com.anas.mymall.CartItemModel;
import com.anas.mymall.DatabaseQueries;
import com.anas.mymall.DeliveryActivity;
import com.anas.mymall.R;
import com.anas.mymall.WishListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private RecyclerView cartItemRecyclerView;
    private Button continueBtn;
    private Dialog loadingDialog;
    private TextView total_cart_amount;
    public static CartAdapter cartAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        //----------Loading_Dilaog---------------
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //----------Loading_Dilaog---------------
        total_cart_amount=view.findViewById(R.id.total_cart_amount);

        continueBtn = view.findViewById(R.id.cart_continue_btn);
        cartItemRecyclerView = view.findViewById(R.id.cart_fragment_receyclerView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        cartItemRecyclerView.setLayoutManager(linearLayout);


        if (DatabaseQueries.cartModelsList.size() == 0) {
            DatabaseQueries.cartListModels_ID.clear();
            DatabaseQueries.loadCartList(getContext(), loadingDialog, true);
        } else {
            loadingDialog.dismiss();
        }

        cartAdapter = new CartAdapter(DatabaseQueries.cartModelsList,total_cart_amount);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                DatabaseQueries.loadAddresses(getContext(),loadingDialog);
            }
        });
        return view;

    }
}