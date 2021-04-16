package com.anas.mymall;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyMallPageAdapter extends RecyclerView.Adapter {
    private List<MyMallPageModel> myMallPageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public MyMallPageAdapter(List<MyMallPageModel> myMallPageModelList) {
        this.myMallPageModelList = myMallPageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (myMallPageModelList.get(position).getType()) {
            case 0:
                return MyMallPageModel.BANNER_SLIDER;
            case 1:
                return MyMallPageModel.STRIP_AD_PANNER;
            case 2:
                return MyMallPageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return MyMallPageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MyMallPageModel.BANNER_SLIDER:
                View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_ad_layout, parent, false);
                return new BannerSliderViewHolder(bannerView);
            case MyMallPageModel.STRIP_AD_PANNER:
                View stripView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdViewHolder(stripView);
            case MyMallPageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalViewHolder(horizontalView);
            case MyMallPageModel.GRID_PRODUCT_VIEW:
                View gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridViewHolder(gridView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (myMallPageModelList.get(position).getType()) {
            case MyMallPageModel.BANNER_SLIDER:
                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(myMallPageModelList.get(position).getSliderModelList());
                break;
            case MyMallPageModel.STRIP_AD_PANNER:
                String resource = myMallPageModelList.get(position).getResource();
                String color = myMallPageModelList.get(position).getBackgroundColor();

                ((StripAdViewHolder) holder).setStripAd(resource, color);
                break;
            case MyMallPageModel.HORIZONTAL_PRODUCT_VIEW:
                String title = myMallPageModelList.get(position).getTitle();
                String bg_tint=myMallPageModelList.get(position).getBgTint();
                List<WishListModel> viewAllProducts=myMallPageModelList.get(position).getViewAllProductList();
                List<HorizontalProductModel> horizontalProductModelList = myMallPageModelList.get(position).getHorizontalProductModelList();
                ((HorizontalViewHolder) holder).setHorizontalProductLayout(horizontalProductModelList, title,bg_tint,viewAllProducts);
                break;
            case MyMallPageModel.GRID_PRODUCT_VIEW:
                String title1 = myMallPageModelList.get(position).getTitle();
                String colorTint=myMallPageModelList.get(position).getBgTint();
                List<HorizontalProductModel> gridProductModelList = myMallPageModelList.get(position).getHorizontalProductModelList();
                ((GridViewHolder) holder).setGridProductLayoutView(gridProductModelList, title1,colorTint);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return myMallPageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        //-----------banner slider--------------
        private ViewPager bannerSliderViewPager;
        private int CurrentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        //-----------banner slider--------------
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.home_bannerSlider_viewPager);
        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdapter);

            bannerSliderViewPager.setPageMargin(20);

            startBannerSliderShow(sliderModelList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    stopBannerSliderShow(sliderModelList);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSliderShow(sliderModelList);
                    }
                    return false;
                }
            });
        }

        private void startBannerSliderShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (CurrentPage >= sliderModelList.size()) {
                        CurrentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(CurrentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSliderShow(List<SliderModel> sliderModelList) {
            timer.cancel();
        }
    }

    public static class StripAdViewHolder extends RecyclerView.ViewHolder {

        public static ImageView stripImage;
        private ConstraintLayout stripAdContainer;

        public StripAdViewHolder(@NonNull View itemView) {
            super(itemView);
            stripImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        public void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.ad_photo)).into(stripImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout constraintLayout;
        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllBtn;
        private RecyclerView horizontalRecyclerView;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle = itemView.findViewById(R.id.h_l_title);
            horizontalLayoutViewAllBtn = itemView.findViewById(R.id.h_l_btn);
            horizontalRecyclerView = itemView.findViewById(R.id.h_l_recycler);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
            constraintLayout=itemView.findViewById(R.id.horizontal_constrain);


        }

        private void setHorizontalProductLayout(List<HorizontalProductModel> horizontalProductModelList, final String title, String bg_tint, final List<WishListModel> viewALlProductList) {

            horizontalLayoutTitle.setText(title);
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bg_tint)));

            if (horizontalProductModelList.size() > 8) {
                horizontalLayoutViewAllBtn.setVisibility(View.VISIBLE);
                horizontalLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishListModelList=viewALlProductList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code", 0);
                        viewAllIntent.putExtra("title",title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                horizontalLayoutViewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalScrollAdapter horizontalScrollAdapter = new HorizontalScrollAdapter(horizontalProductModelList);
            LinearLayoutManager linearLayout = new LinearLayoutManager(itemView.getContext());
            linearLayout.setOrientation(RecyclerView.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayout);
            horizontalRecyclerView.setAdapter(horizontalScrollAdapter);
            horizontalScrollAdapter.notifyDataSetChanged();

        }
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;
        private ConstraintLayout constraintLayout;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewAllBtn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
            constraintLayout=itemView.findViewById(R.id.grid_container);


        }

        private void setGridProductLayoutView(final List<HorizontalProductModel> gridLayoutViewList, final String title, String color_tint) {
            gridLayoutTitle.setText(title);
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color_tint)));
            for (int x = 0; x < 4; x++) {
                ImageView product_image = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView product_title = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                TextView product_description = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_desc);
                TextView product_price = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

               // product_image.setImageResource(gridLayoutViewList.get(x).getProductImage());
                Glide.with(itemView.getContext()).load(gridLayoutViewList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.logo)).into(product_image);
                product_title.setText(gridLayoutViewList.get(x).getProductTitle());
                product_description.setText(gridLayoutViewList.get(x).getProductDesc());
                product_price.setText(gridLayoutViewList.get(x).getProductPrice());

                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productDetailIntent);
                    }
                });

            }
            gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalProductModelList=gridLayoutViewList;
                    Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code", 1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }

}
