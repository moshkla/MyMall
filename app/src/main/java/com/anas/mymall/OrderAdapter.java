package com.anas.mymall;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    List<OrderItemModel> orderItemModelList;

    public OrderAdapter(List<OrderItemModel> orderItemModelList) {
        this.orderItemModelList = orderItemModelList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {
        OrderItemModel source=orderItemModelList.get(position);
        holder.setData(source.getProductImage(),source.getPrductTitle(),source.getDelivaryStatus(),source.getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prouctDetailIntent=new Intent(holder.itemView.getContext(),OrderDetailsActivity.class);
                holder.itemView.getContext().startActivity(prouctDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemModelList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView deliveryIndicator;
        private TextView productTitle;
        private TextView  deliveryStatus;
        private RatingBar productRating;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.order_product_image);
            deliveryIndicator    =itemView.findViewById(R.id.order_item_indicator);
            productTitle    =itemView.findViewById(R.id.order_product_title);
            deliveryStatus  =itemView.findViewById(R.id.order_item_deliverd_date);
            productRating=itemView.findViewById(R.id.order_item_ratingbar);

        }
        private void setData(int resource,String title,String delivereDate,int rating){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (deliveryIndicator.equals("Cancelled")){
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(Color.RED));
                deliveryStatus.setText(delivereDate);
            }else {
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(Color.GREEN));
                deliveryStatus.setText(delivereDate);
            }
            productRating.setRating(rating);

        }
    }
}
