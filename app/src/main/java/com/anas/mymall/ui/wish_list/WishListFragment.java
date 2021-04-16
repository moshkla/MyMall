package com.anas.mymall.ui.wish_list;

import android.app.Dialog;
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

import com.anas.mymall.DatabaseQueries;
import com.anas.mymall.R;
import com.anas.mymall.WishListAdapter;

public class WishListFragment extends Fragment {

    public static WishListAdapter wishListAdapter;
    private Dialog loadingDialog;
    private WishListViewModel wishListViewModel;
    private RecyclerView RecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wishListViewModel =
                ViewModelProviders.of(this).get(WishListViewModel.class);
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        RecyclerView = view.findViewById(R.id.wishList_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(androidx.recyclerview.widget.RecyclerView.VERTICAL);
        RecyclerView.setLayoutManager(layoutManager);

        //----------Loading_Dilaog---------------
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //----------Loading_Dilaog---------------

        if (DatabaseQueries.wishListModelsList.size() == 0) {
            DatabaseQueries.wishListModels_ID.clear();
            DatabaseQueries.loadWishList(getContext(), loadingDialog, true);
        } else {
            loadingDialog.dismiss();
        }

        wishListAdapter = new WishListAdapter(DatabaseQueries.wishListModelsList, true);
        RecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();
        return view;
    }
}