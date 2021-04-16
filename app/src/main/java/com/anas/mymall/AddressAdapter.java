package com.anas.mymall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.anas.mymall.DeliveryActivity.SELECTED_ADDRESS;
import static com.anas.mymall.MyAdressesActivity.refreshItem;
import static com.anas.mymall.ui.account.AccountFragment.MANAGE_ADDRESS;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    List<AddressModel> addressModelList;
    private int MODE;
    private int preSelectedPostion = -1;

    public AddressAdapter(List<AddressModel> addressModelList, int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
    }


    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel source = addressModelList.get(position);
        Boolean selected = source.getSelected();
        holder.setData(source.getFullName(), source.getAddress(), source.getPinCode(), selected, position);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView fullName;
        private TextView address;
        private TextView pinCode;
        private ImageView icon;
        private LinearLayout optionContainer;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.address_item_fullName);
            address = itemView.findViewById(R.id.address_item_addresstext);
            pinCode = itemView.findViewById(R.id.address_item_pinCode);
            icon = itemView.findViewById(R.id.address_item_icon);
            optionContainer = itemView.findViewById(R.id.address_item_option_container);
        }

        private void setData(String name, String addressText, String pinCodeText, Boolean Selected, final int position) {
            fullName.setText(name);
            address.setText(addressText);
            pinCode.setText(pinCodeText);
            if (MODE == SELECTED_ADDRESS) {
                icon.setImageResource(R.drawable.ic_done);
                if (Selected) {
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPostion = position;
                } else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelectedPostion != position) {
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(preSelectedPostion).setSelected(false);
                            refreshItem(preSelectedPostion, position);
                            preSelectedPostion = position;
                        }
                    }
                });

            } else if (MODE == MANAGE_ADDRESS) {
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.ic_vdots);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPostion, preSelectedPostion);
                        preSelectedPostion = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPostion, preSelectedPostion);
                        preSelectedPostion = -1; 
                    }
                });
            }
        }
    }
}
