package com.anas.mymall;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.anas.mymall.ui.cart.CartFragment;
import com.anas.mymall.ui.myMall.MyMallFragment;
import com.anas.mymall.ui.wish_list.WishListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DatabaseQueries {
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelsList = new ArrayList<>();
    public static List<MyMallPageModel> myMallPageModels = new ArrayList<>();

    public static List<List<MyMallPageModel>> lists = new ArrayList<>();
    public static List<String> loadCategoriesNames = new ArrayList<>();
    private static String dataRefrence;
    public static List<String> wishListModels_ID = new ArrayList<>();
    public static List<WishListModel> wishListModelsList = new ArrayList<>();
    public static List<String> myRated_IDs = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();
    public static List<String> cartListModels_ID = new ArrayList<>();
    public static List<CartItemModel> cartModelsList = new ArrayList<>();
    public static List<AddressModel> addressModelList = new ArrayList<>();
    public static int selectedAddress=-1;
    public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context) {
        categoryModelsList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        categoryModelsList.add(new CategoryModel(document.get("icon").toString(), document.get("categoryName").toString()));
                    }
                    categoryAdapter.notifyDataSetChanged();

                } else {
                    String Error = task.getException().getMessage();
                    Toast.makeText(context, Error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void loadFragmentData(final MyMallPageAdapter adapter, final Context context, final int index, String categoryName) {
        switch (categoryName) {
            case "Home":
                dataRefrence = "Qxr1cJ5AkbCHqMmnAWSf";
                break;
            case "Electronics":
                dataRefrence = "ywC66F47P01VKha9IhMj";
                break;
            case "Fashion":
                dataRefrence = "UTInTm3PZaGGe7IscsEq";
                break;
            case "Phones":
                dataRefrence = "A1I3NxS3DMcNgqkhlZm7";
                break;
            case "Shoes":
                dataRefrence = "bHZH7nHJhpz4ntBPDPWo";
                break;
            case "Books":
                dataRefrence = "rDk1I0AKWhXyW2fnWtjf";
                break;
            case "Furniture":
                dataRefrence = "Ur9ZovuLPl9HPvpPP7dK";
                break;
            case "Sports":
                dataRefrence = "Qcms1do5sTQjLdzdMiYyJ";
                break;
            case "Toys":
                dataRefrence = "LdvWuciwlXNAtnftIQ47";
                break;
            default:
                dataRefrence = "Qxr1cJ5AkbCHqMmnAWSf";
        }
        firebaseFirestore.collection("CATEGORIES").document(dataRefrence).collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if ((long) document.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long num_of_banners = (long) document.get("num_of_banners");

                                    for (long x = 1; x < num_of_banners + 1; x++) {
                                        sliderModelList.add(new SliderModel(document.get("banner" + x).toString(), document.get("banner_bg_" + x).toString()));
                                    }
                                    // myMallPageModels.add(new MyMallPageModel(0, sliderModelList));
                                    lists.get(index).add(new MyMallPageModel(0, sliderModelList));
                                } else if ((long) document.get("view_type") == 1) {
                                    lists.get(index).add(new MyMallPageModel(1, document.get("strip1").toString()
                                            , document.get("strip1_bg").toString()));

                                } else if ((long) document.get("view_type") == 2) {

                                    List<WishListModel> viewAllProducts = new ArrayList<>();
                                    List<HorizontalProductModel> horizontalProductModelList = new ArrayList<>();
                                    long num_of_products = (long) document.get("num_of_products");

                                    for (long x = 1; x < num_of_products + 1; x++) {

                                        horizontalProductModelList.add(new HorizontalProductModel(document.get("product_ID_" + x).toString()
                                                , document.get("product_Image_" + x).toString()
                                                , document.get("product_Title_" + x).toString()
                                                , document.get("product_SubTitle_" + x).toString()
                                                , document.get("product_Price_" + x).toString()));

                                        viewAllProducts.add(new WishListModel(
                                                document.get("product_ID_" + x).toString()
                                                , document.get("product_Image_" + x).toString()
                                                , document.get("product_FullName_" + x).toString()
                                                , (long) document.get("free_coupens_" + x)
                                                , document.get("average_rating_" + x).toString()
                                                , (long) document.get("total_ratings_" + x)
                                                , document.get("product_Price_" + x).toString()
                                                , document.get("cuttedPrice_" + x).toString()
                                                , (boolean) document.get("COD_" + x)
                                        ));
                                    }
                                    lists.get(index).add(new MyMallPageModel(2, document.get("layout_title").toString(), document.get("layout_bg").toString(), horizontalProductModelList, viewAllProducts));
                                } else if ((long) document.get("view_type") == 3) {
                                    List<HorizontalProductModel> gridProductModelList = new ArrayList<>();
                                    long num_of_products = (long) document.get("num_of_products");

                                    for (long x = 1; x < num_of_products + 1; x++) {

                                        gridProductModelList.add(new HorizontalProductModel(document.get("product_ID_" + x).toString()
                                                , document.get("product_Image_" + x).toString()
                                                , document.get("product_Title_" + x).toString()
                                                , document.get("product_SubTitle_" + x).toString()
                                                , document.get("product_Price_" + x).toString()));
                                    }
                                    lists.get(index).add(new MyMallPageModel(3, document.get("layout_title").toString(), document.get("layout_bg").toString(), gridProductModelList));
                                }
                            }

                            adapter.notifyDataSetChanged();
                            MyMallFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String Error = task.getException().getMessage();
                            Toast.makeText(context, Error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishListModels_ID.clear();
        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                .collection("USER_DATA")
                .document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishListModels_ID.add(task.getResult().get("product_ID_" + x).toString());
                        if (wishListModels_ID.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.ADDED_TO_WISHLIST = true;
                            if (ProductDetailsActivity.addToWishListBtn != null) {
                                ProductDetailsActivity.addToWishListBtn.setImageResource(R.drawable.ic_wishlist_red_icon);
                            }
                        } else {
                            if (ProductDetailsActivity.addToWishListBtn != null) {
                                ProductDetailsActivity.addToWishListBtn.setImageResource(R.drawable.ic_wishlist_red_icon);
                            }
                            ProductDetailsActivity.ADDED_TO_WISHLIST = false;
                        }
                        if (loadProductData) {
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                wishListModelsList.add(new WishListModel(
                                                        productId
                                                        , task.getResult().get("product_image_1").toString()
                                                        , task.getResult().get("product_title").toString()
                                                        , (long) task.getResult().get("free_coupens")
                                                        , task.getResult().get("average_ratings").toString()
                                                        , (long) task.getResult().get("total_ratings")
                                                        , task.getResult().get("product_price").toString()
                                                        , task.getResult().get("cutted_price").toString()
                                                        , (boolean) task.getResult().get("COD")));
                                                WishListFragment.wishListAdapter.notifyDataSetChanged();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishList(final int index, final Context context) {
        wishListModels_ID.remove(index);
        // final String removedProductId=wishListModels_ID.get(index);

        Map<String, Object> updateWishList = new HashMap<>();

        for (int x = 0; x < wishListModels_ID.size(); x++) {
            updateWishList.put("product_ID_" + x, wishListModels_ID.get(x));
        }
        updateWishList.put("list_size", (long) wishListModels_ID.size());
        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishListModels_ID.size() != 0) {
                        wishListModelsList.remove(index);
                    }
                    ProductDetailsActivity.ADDED_TO_WISHLIST = false;
                    WishListFragment.wishListAdapter.notifyDataSetChanged();

                    Toast.makeText(context, "Removed Successfully", Toast.LENGTH_LONG).show();

                } else {
                    ProductDetailsActivity.addToWishListBtn.setImageResource(R.drawable.ic_wishlist_red_icon);
                    //  wishListModels_ID.add(index,removedProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
                //ProductDetailsActivity.addToWishListBtn.setEnabled(true);
                ProductDetailsActivity.running_wishlist_query = false;
                WishListFragment.wishListAdapter.notifyDataSetChanged();

            }
        });
    }

    public static void loadRatingList(final Context context) {


        if (!ProductDetailsActivity.running_wishlist_query) {
            ProductDetailsActivity.running_wishlist_query = true;
            myRated_IDs.clear();
            myRating.clear();
            firebaseFirestore.collection("USERS")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("USER_DATA")
                    .document("MY_RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            myRated_IDs.add(task.getResult().get("product_ID_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));

                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                ProductDetailsActivity.intialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x)));
                                if (ProductDetailsActivity.rateNow != null) {
                                    ProductDetailsActivity.rateNow.setRating(ProductDetailsActivity.intialRating);

                                }
                            }
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData) {
        cartListModels_ID.clear();
        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                .collection("USER_DATA")
                .document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        cartListModels_ID.add(task.getResult().get("product_ID_" + x).toString());
                        if (cartListModels_ID.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.ADDED_TO_CARTLIST = true;
                        } else {

                            ProductDetailsActivity.ADDED_TO_CARTLIST = false;
                        }
                        if (loadProductData) {
                            cartModelsList.clear();
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                cartModelsList.add(new CartItemModel(
                                                        CartItemModel.CART_ITEM
                                                        , productId
                                                        , task.getResult().get("product_image_1").toString()
                                                        , task.getResult().get("product_title").toString()
                                                        , (long) task.getResult().get("free_coupens")
                                                        , task.getResult().get("product_price").toString()
                                                        , task.getResult().get("cutted_price").toString()
                                                        , (long) 1
                                                        , (long) 1
                                                        , (long) 1));
                                                CartFragment.cartAdapter.notifyDataSetChanged();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromCartList(final int index, final Context context) {
        cartListModels_ID.remove(index);
        //final String removedProductId=cartListModels_ID.get(index);
        Map<String, Object> updateCartList = new HashMap<>();

        for (int x = 0; x < cartListModels_ID.size(); x++) {
            updateCartList.put("product_ID_" + x, cartListModels_ID.get(x));
        }
        updateCartList.put("list_size", (long) cartListModels_ID.size());
        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartListModels_ID.size() != 0) {
                        cartModelsList.remove(index);
                    }
                    ProductDetailsActivity.ADDED_TO_CARTLIST = false;
                    CartFragment.cartAdapter.notifyDataSetChanged();
                    if (ProductDetailsActivity.cartItem != null) {
                        ProductDetailsActivity.cartItem.setActionView(null);
                    }
                    Toast.makeText(context, "Removed Successfully", Toast.LENGTH_LONG).show();

                } else {
                    // cartListModels_ID.add(index,removedProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
                ProductDetailsActivity.running_cartlist_query = false;
                CartFragment.cartAdapter.notifyDataSetChanged();

            }
        });
    }

    public static void loadAddresses(final Context context, final Dialog loadingDialog) {
        addressModelList.clear();
        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Intent deliveryIntent;
                    if ((long)task.getResult().get("list_size") == 0) {
                        deliveryIntent = new Intent(context, AddAddressActivity.class);
                    } else {
                        for (long x = 1; x < (long) task.getResult().get("list_size")+1; x++) {
                            addressModelList.add(new AddressModel(
                                    task.getResult().get("fullName_" + x).toString(),
                                    task.getResult().get("address_" + x).toString(),
                                    task.getResult().get("pinCode_" + x).toString(),
                                    (boolean) task.getResult().get("selected_" + x)
                            ));
                            if ((boolean) task.getResult().get("selected_" + x)) {
                                selectedAddress= Integer.parseInt(String.valueOf(x-1));
                            }
                        }
                        deliveryIntent = new Intent(context, DeliveryActivity.class);
                    }
                    context.startActivity(deliveryIntent);

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
                loadingDialog.dismiss();

            }
        });

    }

    public static void clearData() {
        categoryModelsList.clear();
        lists.clear();
        loadCategoriesNames.clear();
        wishListModels_ID.clear();
        wishListModelsList.clear();
    }
}
