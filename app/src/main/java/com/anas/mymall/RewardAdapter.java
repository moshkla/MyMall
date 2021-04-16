package com.anas.mymall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
   private List<RewardModel> rewardModelList;
    private Boolean useMiniLayout=false;
    public RewardAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (useMiniLayout){
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout, parent, false);
            return new RewardViewHolder(view);
        }else{
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);
            return new RewardViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        RewardModel source=rewardModelList.get(position);
        holder.setData(source.getTitle(),source.getExpiryDate(),source.getCouponBody());
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class RewardViewHolder extends RecyclerView.ViewHolder {
        private TextView coupenTitle;
        private TextView expiryDate;
        private TextView couponBody;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.reward_item_title);
            expiryDate = itemView.findViewById(R.id.reward_item_expiry_date);
            couponBody = itemView.findViewById(R.id.reward_item_body);
        }

        private void setData(final String title, final String expiryDateText, final String couonBodyText) {
            coupenTitle.setText(title);
            expiryDate.setText(expiryDateText);
            couponBody.setText(couonBodyText);

            if (useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.couponTitle.setText(title);
                        ProductDetailsActivity.couponExperiyDate.setText(expiryDateText);
                        ProductDetailsActivity.couponBody.setText(couonBodyText);
                        ProductDetailsActivity.couponRecyclerView.setVisibility(View.GONE);
                        ProductDetailsActivity.selectedCoupon.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }
}
