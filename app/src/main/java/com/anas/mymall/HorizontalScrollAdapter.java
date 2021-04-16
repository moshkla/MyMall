package com.anas.mymall;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.HorizontalViewHolder> {
    private List<HorizontalProductModel> horizontalProductModels;

    public HorizontalScrollAdapter(List<HorizontalProductModel> horizontalProductModels) {
        this.horizontalProductModels = horizontalProductModels;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item_layout,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(horizontalProductModels.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.logo)).into(holder.productImage);
       // holder.productImage.setImageResource(horizontalProductModels.get(position).getProductImage());
        holder.productTitle.setText(horizontalProductModels.get(position).getProductTitle());
        holder.productDesc.setText(horizontalProductModels.get(position).getProductDesc());
        holder.productPrice.setText(horizontalProductModels.get(position).getProductPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetailIntent=new Intent(holder.itemView.getContext(),ProductDetailsActivity.class);
                holder.itemView.getContext().startActivity(productDetailIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return horizontalProductModels.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDesc;
        private TextView productPrice;
        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.h_s_product_image);
            productTitle=itemView.findViewById(R.id.h_s_product_title);
            productDesc=itemView.findViewById(R.id.h_s_product_desc);
            productPrice=itemView.findViewById(R.id.h_s_product_price);
        }
    }
}
