package com.anas.mymall;

import android.animation.Animator;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHlder> {
    List<WishListModel> wishListModelList;
    Boolean wishList;

    public WishListAdapter(List<WishListModel> wishListModelList, Boolean wishList) {
        this.wishListModelList = wishListModelList;
        this.wishList = wishList;
    }

    @NonNull
    @Override
    public WishListViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new WishListViewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WishListViewHlder holder, int position) {
        WishListModel source = wishListModelList.get(position);
        holder.setData(
                source.getProductId()
                , source.getProductimage()
                , source.getProductName()
                , source.getRatings()
                , source.getTotalRatings()
                , source.getFreeCoupons()
                , source.getProductPrice()
                , source.getCuttedPrice()
                , source.getCOD()
                , position);


    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class WishListViewHlder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView prductTitle;
        private TextView ratings;
        private TextView totalRatings;
        private TextView freeCoupen;
        private ImageView coupenIcon;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;

        public WishListViewHlder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.wishList_item_image);
            prductTitle = itemView.findViewById(R.id.wishList_item_title);
            ratings = itemView.findViewById(R.id.wishList_rating_miniview);
            totalRatings = itemView.findViewById(R.id.wishList_item_totalratings);
            freeCoupen = itemView.findViewById(R.id.wishList_item_freecoupens);
            coupenIcon = itemView.findViewById(R.id.wishList_item_freecoupens_icon);
            productPrice = itemView.findViewById(R.id.wishList_item_price);
            cuttedPrice = itemView.findViewById(R.id.wishList_item_cuttedprice);
            paymentMethod = itemView.findViewById(R.id.wishList_item_payment_method);
            deleteBtn = itemView.findViewById(R.id.wishList_item_delete_btn);

        }

        private void setData(final String productId, String resource, String prductTitleText, String ratingsText, long totalRatingsText, long freeCoupenNO, String productPriceText, String cuttedPriceText, boolean COD, final int index) {
            // productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.phone_image)).into(productImage);
            prductTitle.setText(prductTitleText);
            if (freeCoupenNO != 0) {
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupenNO == 1) {
                    freeCoupen.setText("free " + freeCoupenNO + "coupon");
                } else {
                    freeCoupen.setText("free " + freeCoupenNO + "coupons");

                }
            } else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupen.setVisibility(View.INVISIBLE);
            }
            ratings.setText(ratingsText);
            totalRatings.setText(totalRatingsText + " ratings");

            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if (COD) {
                paymentMethod.setVisibility(View.VISIBLE);
            } else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }
            if (wishList) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetailIntent.putExtra("PRODUCT_ID", productId);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_wishlist_query) {
                        ProductDetailsActivity.running_wishlist_query=true;
                        DatabaseQueries.removeFromWishList(index, itemView.getContext());
                        wishListModelList.remove(index);
                    }

                }
            });
        }
    }
}
