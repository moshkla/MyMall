package com.anas.mymall;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.anas.mymall.LoginActivity.setSignUpFragment;

public class CartAdapter extends RecyclerView.Adapter {
    List<CartItemModel> cartItemModelList;
    private TextView cartTotalAmount;

    public CartAdapter(List<CartItemModel> cartItemModelList, TextView cartTotalAmount) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
    }

    @Override
    public int getItemViewType(int position) {

        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUBT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new CartItemViewHolder(cartItemview);

            case CartItemModel.TOTAL_AMOUBT:
                View cartTotalAmountView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount, parent, false);
                return new CartTotalAmountViewHolder(cartTotalAmountView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                CartItemModel resource = cartItemModelList.get(position);
                ((CartItemViewHolder) holder).setItemDetails(
                        resource.getProductID()
                        , resource.getProductImage()
                        , resource.getProductTitle()
                        , resource.getFreeCoupens()
                        , resource.getProductPrice()
                        , resource.getCuttedPrice()
                        , resource.getOffersApplied()
                        , position);
                break;
            case CartItemModel.TOTAL_AMOUBT:
                CartItemModel resource2 = cartItemModelList.get(position);

                ((CartTotalAmountViewHolder) holder).setCartAmount(resource2.getTotalItems(), resource2.getTotalItemPrice(), resource2.getDelivaryPrice(), resource2.getTotalAmount(), resource2.getSaveAmount());
                break;
            default:
                return;
        }
    }


    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }


    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView freeCoupenIcon;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView productQuantity;
        private TextView offersApplied;
        private TextView coupensApplied;
        private LinearLayout removeItem;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_item_image);
            productTitle = itemView.findViewById(R.id.cart_item_title);
            freeCoupens = itemView.findViewById(R.id.cart_item_freecoupens);
            freeCoupenIcon = itemView.findViewById(R.id.cart_item_freecoupens_icon);
            productPrice = itemView.findViewById(R.id.cart_item_cuttedprice);
            cuttedPrice = itemView.findViewById(R.id.cart_item_cuttedprice);
            productQuantity = itemView.findViewById(R.id.cart_item_product_quantity);
            offersApplied = itemView.findViewById(R.id.cart_item_payment_method);
            coupensApplied = itemView.findViewById(R.id.cart_item_coupen_applied);
            removeItem = itemView.findViewById(R.id.cart_item_remove);
        }

        private void setItemDetails(String productId, String resource, String title, long freeCoupensNo, String productPriceText, String productCuttedPriceText, long offersAppliedNo, final int index) {

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.logo)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo > 0) {
                freeCoupenIcon.setVisibility(View.VISIBLE);
                freeCoupens.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {
                    freeCoupens.setText("free " + freeCoupensNo + " coupon");
                } else {
                    freeCoupens.setText("free " + freeCoupensNo + " coupons");

                }

            } else {
                freeCoupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(productCuttedPriceText);
            if (offersAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + " Offers Applied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }

            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.setCancelable(true);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button Done_Btn = quantityDialog.findViewById(R.id.quantity_dialog_doneBtn);
                    Done_Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                            EditText quantityText = quantityDialog.findViewById(R.id.quantity_dialog_quantityText);
                            productQuantity.setText("Qty : " + quantityText.getText().toString());
                        }
                    });

                    quantityDialog.show();
                }
            });

            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_cartlist_query) {
                        ProductDetailsActivity.running_cartlist_query = true;
                        DatabaseQueries.removeFromCartList(index, itemView.getContext());
                        cartItemModelList.remove(index);
                    }
                }
            });
        }
    }

    public class CartTotalAmountViewHolder extends RecyclerView.ViewHolder {
        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView totalAmount;
        private TextView delivaryPrice;
        private TextView saveAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.cart_totalAmount_total_items);
            totalItemsPrice = itemView.findViewById(R.id.cart_totalAmount_total_items_price);

            delivaryPrice = itemView.findViewById(R.id.cart_totalAmount_delivery_price);
            totalAmount = itemView.findViewById(R.id.cart_totalAmount);
            saveAmount = itemView.findViewById(R.id.cart_totalAmount_saved_price);
        }

        private void setCartAmount(String totalItemText, String totalItemsPriceText, String delivaryPriceText, String totalAmountText, String saveAmountText) {
            totalItems.setText(totalItemText);
            totalItemsPrice.setText(totalItemsPriceText);
            delivaryPrice.setText(delivaryPriceText);
            totalAmount.setText(totalAmountText);
            cartTotalAmount.setText(totalAmountText);
            saveAmount.setText(saveAmountText);
        }
    }
}
