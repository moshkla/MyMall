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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryModel> categoryModels;

    public CategoryAdapter(List<CategoryModel> categoryModels) {
        this.categoryModels=categoryModels;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {
        holder.cat_text_item.setText(categoryModels.get(position).getCategoryName());
        holder.setCategoryIcon(categoryModels.get(position).getCategoryIconLink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cat_intent=new Intent(holder.itemView.getContext(),CategoryActivity.class);
                cat_intent.putExtra("CategoryName",categoryModels.get(position).getCategoryName());
                holder.itemView.getContext().startActivity(cat_intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView cat_image_item;
        private TextView cat_text_item;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_image_item = itemView.findViewById(R.id.cat_image_icon);
            cat_text_item = itemView.findViewById(R.id.cat_text_item);
        }
        private void setCategoryIcon(String icon) {
            if (!icon.equals("null")) {
                Glide.with(itemView.getContext()).load(icon).apply(new RequestOptions().placeholder(R.drawable.ic_home)).into(cat_image_item);
            }
        }
    }
}
