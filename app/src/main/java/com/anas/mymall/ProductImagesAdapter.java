package com.anas.mymall;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductImagesAdapter extends PagerAdapter {
    private List<String> productImagesList;

    public ProductImagesAdapter(List<String> productImagesList) {
        this.productImagesList = productImagesList;
    }

    @Override
    public int getCount() {
        return productImagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImageView = new ImageView(container.getContext());

       // productImageView.setImageResource(productImagesList.get(position));
        Glide.with(container.getContext()).load(productImagesList.get(position)).apply(new RequestOptions().placeholder(R.mipmap.logo)).into(productImageView);
        container.addView(productImageView);
        return productImageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
