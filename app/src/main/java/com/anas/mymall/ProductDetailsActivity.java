package com.anas.mymall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.mymall.ui.cart.CartFragment;
import com.anas.mymall.ui.wish_list.WishListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.anas.mymall.DatabaseQueries.currentUser;
import static com.anas.mymall.DatabaseQueries.firebaseAuth;
import static com.anas.mymall.DatabaseQueries.wishListModelsList;
import static com.anas.mymall.LoginActivity.setSignUpFragment;
import static com.anas.mymall.RegisterFragment.CLOSE_BTN;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout productViewPagerIndicator;
    //----------Product_Description---------------
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;

    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsViewPagerIndicator;

    private TextView productOnlyDescriptionBody;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    //----------Product_Description---------------

    public static FloatingActionButton addToWishListBtn;
    public static boolean ADDED_TO_WISHLIST = false;
    public static boolean ADDED_TO_CARTLIST = false;


    private Button buyNowBtn;
    private Button redemption_btn;
    private LinearLayout addToCartBtn;

    public static RatingBar rateNow;
    private TextView productTitle;
    //----------Redeem Dialog---------------
    public static TextView couponTitle;
    public static TextView couponExperiyDate;
    public static TextView couponBody;
    public static RecyclerView couponRecyclerView;
    public static LinearLayout selectedCoupon;

    private LinearLayout coupon_redemption_layout;
    //----------Redeem Dialog---------------

    private FirebaseFirestore firebaseFirestore;

    private TextView product_item_totalratings;
    private TextView tv_product_rating_miniview;
    private TextView cutted_price;
    private TextView product_item_price;
    private ImageView cod_indicator_imageView;
    private TextView tv_cod_indicator;
    private TextView reward_title;
    private TextView reward_body;

    //----------Product_Rating---------------
    public static int intialRating = -1;
    private TextView totalRating;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout rating_progressbar_container;
    private TextView average_rating;
    public static boolean running_rating_query = false;
    private TextView ratingFigure;
    //----------Product_Rating---------------

    //----------SignIn Dialog---------------
    private Dialog signInDialog;
    //----------SignIn Dialog---------------

    public static String productID = "2zRc2jpQDN8EYOl8g8hJ";
    public static boolean running_wishlist_query = false;
    public static boolean running_cartlist_query = false;


    private Dialog loadingDialog;
    private DocumentSnapshot documentSnapshot;

    public static MenuItem cartItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();

        coupon_redemption_layout = findViewById(R.id.coupon_redemption_layout);

        buyNowBtn = findViewById(R.id.product_buy_now_btn);
        redemption_btn = findViewById(R.id.coupon_redemption_btn);

        productImageViewPager = findViewById(R.id.product_image_viewpager);
        productViewPagerIndicator = findViewById(R.id.product_viewpager_indicator);

        productDetailsViewPager = findViewById(R.id.product_description_ViewPager);
        productDetailsViewPagerIndicator = findViewById(R.id.product_description_TapLayout);

        productTitle = findViewById(R.id.product_layout_title);
        product_item_totalratings = findViewById(R.id.product_item_totalratings);
        tv_product_rating_miniview = findViewById(R.id.tv_product_rating_miniview);
        cutted_price = findViewById(R.id.cutted_price);
        product_item_price = findViewById(R.id.product_item_price);
        cod_indicator_imageView = findViewById(R.id.cod_indicator_imageView);
        tv_cod_indicator = findViewById(R.id.tv_cod_indicator);
        reward_title = findViewById(R.id.reward_title);
        reward_body = findViewById(R.id.reward_body);

        productDetailsTabsContainer = findViewById(R.id.productDetailsTabsLayout);
        productDetailsOnlyContainer = findViewById(R.id.product_details_only_container);

        productOnlyDescriptionBody = findViewById(R.id.product_details_body);

        totalRating = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        average_rating = findViewById(R.id.average_rating);
        rating_progressbar_container = findViewById(R.id.rating_progressbar_container);

        addToCartBtn = findViewById(R.id.product_add_to_cart_btn);
        rateNow = findViewById(R.id.ratingBar);

        //----------Loading_Dilaog---------------
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //----------Loading_Dilaog---------------
        //***********************************************************************************
        if (currentUser == null) {
            coupon_redemption_layout.setVisibility(View.GONE);
        } else {
            coupon_redemption_layout.setVisibility(View.VISIBLE);
        }

        if (currentUser != null) {
            if (DatabaseQueries.wishListModels_ID.size() == 0) {
                DatabaseQueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
            if (DatabaseQueries.cartListModels_ID.size() == 0) {
                DatabaseQueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
            if (DatabaseQueries.myRated_IDs.size() == 0) {
                DatabaseQueries.loadRatingList(ProductDetailsActivity.this);
            }
        } else {
            loadingDialog.dismiss();
        }
        if (DatabaseQueries.myRated_IDs.contains(productID)) {
            int index = DatabaseQueries.myRated_IDs.indexOf(productID);
            intialRating = DatabaseQueries.myRating.indexOf(index) - 1;
            rateNow.setRating(intialRating);
        }
        if (DatabaseQueries.cartListModels_ID.contains(productID)) {
            ADDED_TO_CARTLIST = true;
        } else {
            ADDED_TO_CARTLIST = false;
        }
        if (DatabaseQueries.wishListModels_ID.contains(productID)) {
            ADDED_TO_WISHLIST = true;
            addToWishListBtn.setImageResource(R.drawable.ic_wishlist_red_icon);

        } else {
            // addToWishListBtn.setImageResource(R.drawable.ic_wishable_icon);
            ADDED_TO_WISHLIST = false;
        }

        //****************************************************************************************

        final List<String> productImagesList = new ArrayList<>();
        //  productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document("2zRc2jpQDN8EYOl8g8hJ").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            documentSnapshot = task.getResult();
                            for (long x = 1; x < (long) documentSnapshot.get("num_of_product_image") + 1; x++) {
                                productImagesList.add(documentSnapshot.get(("product_image_") + x).toString());
                            }
                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            product_item_totalratings.setText("(" + documentSnapshot.get("total_ratings") + " ratings )");

                            tv_product_rating_miniview.setText(documentSnapshot.get("average_ratings").toString());
                            cutted_price.setText("(" + documentSnapshot.get("cutted_price").toString() + "LE" + ")");
                            product_item_price.setText(documentSnapshot.get("product_price").toString());
                            ProductImagesAdapter adapter = new ProductImagesAdapter(productImagesList);
                            productImageViewPager.setAdapter(adapter);
                            productViewPagerIndicator.setupWithViewPager(productImageViewPager, true);
                            productTitle.setText(documentSnapshot.get("product_title").toString());

                            if ((boolean) documentSnapshot.get("COD")) {
                                cod_indicator_imageView.setVisibility(View.VISIBLE);
                                tv_cod_indicator.setVisibility(View.VISIBLE);
                            } else {
                                cod_indicator_imageView.setVisibility(View.VISIBLE);
                                tv_cod_indicator.setVisibility(View.VISIBLE);
                            }
                            reward_title.setText(documentSnapshot.get("free_coupen_title").toString());
                            reward_body.setText(documentSnapshot.get("free_coupen_body").toString());


                            if ((boolean) documentSnapshot.get("use_tab_layout")) {

                                productDetailsTabsContainer.setVisibility(View.VISIBLE);
                                productDetailsOnlyContainer.setVisibility(View.GONE);

                                ProductDetailsAdapter.productDescription = documentSnapshot.get("product_description").toString();
                                ProductDetailsAdapter.productOtherDetails = documentSnapshot.get("other_description").toString();
                                for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                                    productSpecificationModelList.add(new ProductSpecificationModel(0
                                            , documentSnapshot.get("spec_title_" + x).toString()));
                                    for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + y + "_total") + 1; y++) {
                                        productSpecificationModelList.add(new ProductSpecificationModel(1
                                                , documentSnapshot.get("spec_title_" + x + "_total_field" + y + "_name").toString()
                                                , documentSnapshot.get("spec_title_" + x + "_total_field" + y + "_value").toString()));
                                    }
                                }
                                ProductSpecificationFragment.specificationAdapter.notifyDataSetChanged();

                            } else {
                                productDetailsTabsContainer.setVisibility(View.GONE);
                                productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                                productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                            }
                            totalRating.setText(documentSnapshot.get("total_ratings").toString() + " ratings");
                            for (int x = 0; x < 5; x++) {
                                ratingFigure = (TextView) ratingsNoContainer.getChildAt(x);
                                ratingFigure.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_stars")));

                                ProgressBar progressBar = (ProgressBar) rating_progressbar_container.getChildAt(x);
                                int Max = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                                progressBar.setMax(Max);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_stars"))));
                            }
                            totalRatingsFigure.setText((documentSnapshot.get("total_ratings").toString() + " ratings"));
                            average_rating.setText(documentSnapshot.get("average_ratings").toString());

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                        }
                    }
                });


        productDetailsViewPager
                .setAdapter(
                        new ProductDetailsAdapter(getSupportFragmentManager()
                                , productDetailsViewPagerIndicator.getTabCount()

                                , productSpecificationModelList));
        productDetailsViewPager
                .addOnPageChangeListener(
                        new TabLayout.TabLayoutOnPageChangeListener(productDetailsViewPagerIndicator));
        productDetailsViewPagerIndicator
                .addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        productDetailsViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


        addToWishListBtn = findViewById(R.id.product_add_to_wishList_btn);

        //-------------ADD && Remove WishListBtn------------------
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    // addToWishListBtn.setEnabled(false);
                    if (running_wishlist_query) {
                        running_wishlist_query = true;
                        if (ADDED_TO_WISHLIST) {
                            running_wishlist_query = false;

                            //remove Item from wishList
                            int index = DatabaseQueries.wishListModels_ID.indexOf(productID);
                            DatabaseQueries.removeFromWishList(index, ProductDetailsActivity.this);
                            addToWishListBtn.setImageResource(R.drawable.ic_wishable_icon);
                        } else {
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + DatabaseQueries.wishListModels_ID.size(), productID);
                            addProduct.put("list_size", (long) DatabaseQueries.wishListModels_ID.size() + 1);

                            firebaseFirestore.collection("USERS")
                                    .document(firebaseAuth.getUid())
                                    .collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                if (DatabaseQueries.wishListModelsList.size() != 0) {
                                                    DatabaseQueries.wishListModelsList.add(new WishListModel(
                                                            productID
                                                            , documentSnapshot.get("product_image_1").toString()
                                                            , documentSnapshot.get("product_title").toString()
                                                            , (long) documentSnapshot.get("free_coupens")
                                                            , documentSnapshot.get("average_rating").toString()
                                                            , (long) documentSnapshot.get("total_ratings")
                                                            , documentSnapshot.get("product_Price").toString()
                                                            , documentSnapshot.get("cutted_price").toString()
                                                            , (boolean) documentSnapshot.get("COD")
                                                    ));
                                                    WishListFragment.wishListAdapter.notifyDataSetChanged();

                                                }

                                                ADDED_TO_WISHLIST = true;
                                                addToWishListBtn.setImageResource(R.drawable.ic_wishlist_red_icon);
                                                DatabaseQueries.wishListModels_ID.add(productID);
                                                Toast.makeText(ProductDetailsActivity.this, "ADDED TO WISHLIST", Toast.LENGTH_LONG).show();

                                            } else {
                                                loadingDialog.dismiss();
                                                //addToWishListBtn.setEnabled(true);

                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                            }
                                            running_wishlist_query = false;

                                        }
                                    });


                        }

                    }
                }
            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }

            }
        });
        //--------------ADD TO CART BTN--------------

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!running_cartlist_query) {
                        running_cartlist_query = true;
                        if (ADDED_TO_CARTLIST) {
                            running_cartlist_query = false;

                            Toast.makeText(ProductDetailsActivity.this, "Already Added To Cart", Toast.LENGTH_LONG).show();
                        } else {
                            Map<String, Object> addToCartProduct = new HashMap<>();
                            addToCartProduct.put("product_ID_" + DatabaseQueries.cartListModels_ID.size(), productID);
                            addToCartProduct.put("list_size", (long) DatabaseQueries.cartListModels_ID.size() + 1);

                            firebaseFirestore.collection("USERS")
                                    .document(firebaseAuth.getUid())
                                    .collection("USER_DATA").document("MY_CART")
                                    .update(addToCartProduct)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                if (DatabaseQueries.cartModelsList.size() != 0) {
                                                    DatabaseQueries.cartModelsList.add(new CartItemModel(
                                                            CartItemModel.CART_ITEM
                                                            , productID
                                                            , documentSnapshot.get("product_image_1").toString()
                                                            , documentSnapshot.get("product_title").toString()
                                                            , (long) documentSnapshot.get("free_coupens")
                                                            , documentSnapshot.get("product_price").toString()
                                                            , documentSnapshot.get("cutted_price").toString()
                                                            , (long) 1
                                                            , (long) 1
                                                            , (long) 1
                                                    ));

                                                    CartFragment.cartAdapter.notifyDataSetChanged();

                                                }

                                                ADDED_TO_CARTLIST = true;
                                                DatabaseQueries.cartListModels_ID.add(productID);
                                                Toast.makeText(ProductDetailsActivity.this, "ADDED TO CARTLIST", Toast.LENGTH_LONG).show();
                                                invalidateOptionsMenu();
                                                running_cartlist_query = false;

                                            } else {
                                                loadingDialog.dismiss();
                                                //addToWishListBtn.setEnabled(true);
                                                running_wishlist_query = false;

                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                        }
                    }

                }

            }
        });

        //--------------ADD TO CART BTN--------------

        //--------------Ratings Layout--------------
        /*
        rateNow.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float rating, boolean fromUser) {
                final long userRating = (long) rating;
                if (currentUser == null) {
                    signInDialog.show();
                } else {

                    if (userRating != intialRating) {
                        if (!running_rating_query) {
                            running_rating_query = true;
                            ratingBar.setRating(userRating - 1);
                            final TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - intialRating - 1);
                            final TextView finalRating = (TextView) ratingsNoContainer.getChildAt((int) (5 - userRating - 2));

                            Map<String, Object> updateRating = new HashMap<>();
                            if (DatabaseQueries.myRated_IDs.contains(productID)) {
                                updateRating.put(intialRating + 1 + "_stars", (Long.parseLong(oldRating.getText().toString()) - 1));
                                updateRating.put(userRating + "_stars", (Long.parseLong(finalRating.getText().toString()) + 1));
                                updateRating.put("average_ratings", calculateAverageRatings(userRating - intialRating, true));
                            } else {
                                updateRating.put(userRating + "_stars", (long) documentSnapshot.get(userRating + "_stars") + 1);
                                updateRating.put("average_ratings", calculateAverageRatings(userRating, false));
                                updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);
                            }
                            firebaseFirestore.collection("PRODUCTS").document(productID)
                                    .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Map<String, Object> myRating = new HashMap<>();

                                        if (DatabaseQueries.myRated_IDs.contains(productID)) {
                                            myRating.put("rating_" + DatabaseQueries.myRated_IDs.indexOf(productID), userRating);
                                        } else {
                                            myRating.put("list_size", DatabaseQueries.myRated_IDs.size() + 1);
                                            myRating.put("product_ID_" + DatabaseQueries.myRated_IDs.size(), productID);
                                            myRating.put("rating_" + DatabaseQueries.myRated_IDs.size(), userRating);
                                        }


                                        firebaseFirestore.collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .collection("USER_DATA")
                                                .document("MY_RATINGS")
                                                .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DatabaseQueries.myRated_IDs.contains(productID)) {
                                                        DatabaseQueries.myRating.set(DatabaseQueries.myRated_IDs.indexOf(productID), userRating);


                                                        oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                        finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));
                                                    } else {


                                                        DatabaseQueries.myRated_IDs.add(productID);
                                                        DatabaseQueries.myRating.add(userRating);
                                                        product_item_totalratings.setText("(" + (long) documentSnapshot.get("total_ratings") + 1 + " ratings" + ")");
                                                        totalRating.setText("(" + (long) documentSnapshot.get("total_ratings") + 1 + " ratings )");

                                                        Toast.makeText(ProductDetailsActivity.this, "Thank You for Rating", Toast.LENGTH_LONG).show();
                                                    }


                                                    for (int x = 0; x < 5; x++) {
                                                        TextView ratingFigure = (TextView) ratingsNoContainer.getChildAt(x);

                                                        ProgressBar progressBar = (ProgressBar) rating_progressbar_container.getChildAt(x);
                                                        int Max = Integer.parseInt(documentSnapshot.get("total_ratings").toString());
                                                        progressBar.setMax(Max);
                                                        progressBar.setProgress(Integer.parseInt(ratingFigure.getText().toString()));
                                                    }

                                                    intialRating = ((int) userRating - 1);
                                                    tv_product_rating_miniview.setText(calculateAverageRatings(0, true));
                                                    average_rating.setText(calculateAverageRatings(0, true));

                                                    if (DatabaseQueries.wishListModels_ID.contains(productID) && wishListModelsList.size() != 0) {
                                                        int index = DatabaseQueries.wishListModels_ID.indexOf(productID);
                                                        DatabaseQueries.wishListModelsList.get(index).setRatings(average_rating.getText().toString());
                                                        DatabaseQueries.wishListModelsList.get(index).setTotalRatings(Long.parseLong(totalRating.getText().toString()));


                                                    }
                                                } else {
                                                    ratingBar.setRating(intialRating);
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                                }
                                                running_rating_query = false;

                                            }
                                        });
                                    } else {
                                        running_rating_query = false;
                                        ratingBar.setRating(intialRating);
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }
                    }
                }
            }
        });
*/
        //--------------Ratings Layout--------------


        //----------Redeem Dialog---------------
        final Dialog redemptionDialog = new Dialog(ProductDetailsActivity.this);
        redemptionDialog.setContentView(R.layout.coupon_redeem_dialog);
        redemptionDialog.setCancelable(true);
        redemptionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final ImageView toggleRecyclerview = redemptionDialog.findViewById(R.id.toggle_recyclerView);
        couponRecyclerView = redemptionDialog.findViewById(R.id.coupons_recyclerView);
        couponRecyclerView.setVisibility(View.GONE);
        selectedCoupon = redemptionDialog.findViewById(R.id.coupon_selected);
        TextView originalPrice = redemptionDialog.findViewById(R.id.couponOriginalPrice);
        TextView redemptionPrice = redemptionDialog.findViewById(R.id.couponRedemptionPrice);
        LinearLayoutManager redeemlayout = new LinearLayoutManager(ProductDetailsActivity.this);
        couponRecyclerView.setLayoutManager(redeemlayout);


        couponTitle = redemptionDialog.findViewById(R.id.reward_item_title);
        couponExperiyDate = redemptionDialog.findViewById(R.id.reward_item_expiry_date);
        couponBody = redemptionDialog.findViewById(R.id.reward_item_body);
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CASH BACK", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("BUY 1 GET 1", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("DISCOUNT", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("CASH BACK", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("BUY 1 GET 1", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("DISCOUNT", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList, true);
        couponRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        //----------Redeem Dialog---------------
        redemption_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleRecyclerview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToggleRecyclerView();
                    }
                });
                redemptionDialog.show();
            }
        });

        //----------SignIn Dialog---------------
        signInDialog = new Dialog(this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signInD_Btn = signInDialog.findViewById(R.id.dialogSignInBtn);
        Button signUpD_Btn = signInDialog.findViewById(R.id.dialogSignUpBtn);
        final Intent loginIntent = new Intent(ProductDetailsActivity.this, LoginActivity.class);
        signInD_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();

                setSignUpFragment = true;
                startActivity(loginIntent);
            }
        });
        signUpD_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLOSE_BTN = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(loginIntent);
            }
        });
        //----------SignIn Dialog---------------

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            coupon_redemption_layout.setVisibility(View.GONE);
        } else {
            coupon_redemption_layout.setVisibility(View.VISIBLE);
        }

        if (currentUser != null) {
            if (DatabaseQueries.wishListModels_ID.size() == 0) {
                DatabaseQueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
            if (DatabaseQueries.cartListModels_ID.size() == 0) {
                DatabaseQueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
            if (DatabaseQueries.myRated_IDs.size() == 0) {
                DatabaseQueries.loadRatingList(ProductDetailsActivity.this);
            }
        } else {
            loadingDialog.dismiss();
        }
        if (DatabaseQueries.myRated_IDs.contains(productID)) {
            int index = DatabaseQueries.myRated_IDs.indexOf(productID);
            intialRating = DatabaseQueries.myRating.indexOf(index) - 1;
            rateNow.setRating(intialRating);
        }
        if (DatabaseQueries.cartListModels_ID.contains(productID)) {
            ADDED_TO_CARTLIST = true;
        } else {
            ADDED_TO_CARTLIST = false;
        }
        if (DatabaseQueries.wishListModels_ID.contains(productID)) {
            ADDED_TO_WISHLIST = true;
            addToWishListBtn.setImageResource(R.drawable.ic_wishlist_red_icon);

        } else {
            addToWishListBtn.setImageResource(R.drawable.ic_wishable_icon);
            ADDED_TO_WISHLIST = false;
        }

    }

    public static void showToggleRecyclerView() {
        if (couponRecyclerView.getVisibility() == View.GONE) {
            couponRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);

        }
    }

    private String calculateAverageRatings(long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * x);
        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf(totalStars / ((long) documentSnapshot.get("total_ratings"))).substring(0, 3);

        } else {
            return String.valueOf(totalStars / ((long) documentSnapshot.get("total_ratings") + 1)).substring(0, 3);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icons, menu);
        cartItem = menu.findItem(R.id.main_cart_icon);
        if (DatabaseQueries.cartListModels_ID.size() > 0) {
            cartItem.setActionView(R.layout.badge_layout);
            ConstraintLayout cartContainer = cartItem.getActionView().findViewById(R.id.new_cart_container);
            TextView badeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            badeCount.setText(String.valueOf(DatabaseQueries.cartListModels_ID.size()));

            cartContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "will show the cart soon", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            cartItem.setActionView(null);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_search_icon:

                break;
            case R.id.home:
                finish();
                break;
            case R.id.main_cart_icon:

                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
