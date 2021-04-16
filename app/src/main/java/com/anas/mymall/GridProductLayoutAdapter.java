package com.anas.mymall;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    List<HorizontalProductModel> models;

    public GridProductLayoutAdapter(List<HorizontalProductModel> models) {
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.WHITE);
            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productDesc = view.findViewById(R.id.h_s_product_desc);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);


           // productImage.setImageResource(models.get(position).getProductImage());
            Glide.with(parent.getContext()).load(models.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.logo)).into(productImage);
            productTitle.setText(models.get(position).getProductTitle());
            productDesc.setText(models.get(position).getProductDesc());
            productPrice.setText(models.get(position).getProductPrice());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent productDetailIntent = new Intent(v.getContext(), ProductDetailsActivity.class);
                    v.getContext().startActivity(productDetailIntent);

                }
            });

        } else {

            view = convertView;
        }

        return view;
    }
}
