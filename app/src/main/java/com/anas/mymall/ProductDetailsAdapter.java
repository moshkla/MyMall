package com.anas.mymall;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totalTaps;
    public static String productDescription;
    public static String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModels;

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int behavior, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm, behavior);

        this.productSpecificationModels = productSpecificationModelList;
        this.totalTaps = behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                productDescriptionFragment.body = productDescription;
                return productDescriptionFragment;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModels;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment other = new ProductDescriptionFragment();
                other.body = productOtherDetails;
                return other;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTaps;
    }
}
