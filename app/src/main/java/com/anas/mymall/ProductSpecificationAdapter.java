package com.anas.mymall;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.SpecificationViewHolder> {
    private List<ProductSpecificationModel> specificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> specificationModelList) {
        this.specificationModelList = specificationModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (specificationModelList.get(position).getType()) {
            case 0:
                return ProductSpecificationModel.SPECIFICATION_TITLE;
            case 1:
                return ProductSpecificationModel.SPECIFICATION_BODY;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public SpecificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setDp(16, parent.getContext()), setDp(16, parent.getContext()), setDp(16, parent.getContext()), setDp(8, parent.getContext()));
                title.setLayoutParams(layoutParams);
                return new SpecificationViewHolder(title);
            case ProductSpecificationModel.SPECIFICATION_BODY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout, parent, false);
                return new SpecificationViewHolder(view);
            default:
                return null;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull SpecificationViewHolder holder, int position) {
        switch (specificationModelList.get(position).getType()) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                holder.setTitle(specificationModelList.get(position).getTitle());
                break;
            case ProductSpecificationModel.SPECIFICATION_BODY:
                holder.setFeature(specificationModelList.get(position).getFeatureName(), specificationModelList.get(position).getFeatureValue());
                break;
            default:
                return;

        }
    }

    @Override
    public int getItemCount() {
        return specificationModelList.size();
    }

    public class SpecificationViewHolder extends RecyclerView.ViewHolder {
        private TextView featureName;
        private TextView featureValue;
        private TextView title;

        public SpecificationViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        private void setTitle(String titleText) {
            title = (TextView) itemView;
            title.setText(titleText);
        }

        private void setFeature(String name, String value) {
            featureName = itemView.findViewById(R.id.featureName);
            featureValue = itemView.findViewById(R.id.feature_value);
            featureName.setText(name);
            featureValue.setText(value);

        }

    }

    private int setDp(int dp, Context context) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
