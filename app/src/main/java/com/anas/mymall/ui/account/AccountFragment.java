package com.anas.mymall.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anas.mymall.DeliveryActivity;
import com.anas.mymall.MyAdressesActivity;
import com.anas.mymall.R;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    public static final int MANAGE_ADDRESS = 1;

    private Button addressViewAllBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        addressViewAllBtn=view.findViewById(R.id.my_address_viewAll_btn);
        addressViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAddressIntent = new Intent(getActivity(), MyAdressesActivity.class);
                MyAddressIntent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(MyAddressIntent);
                getActivity().finish();
            }
        });
        return view;
    }
}