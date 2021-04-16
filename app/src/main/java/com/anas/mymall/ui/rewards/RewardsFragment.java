package com.anas.mymall.ui.rewards;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.mymall.MainActivity;
import com.anas.mymall.R;
import com.anas.mymall.RewardAdapter;
import com.anas.mymall.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class RewardsFragment extends Fragment {

    private RewardsViewModel rewardsViewModel;
    private RecyclerView rewardRecyclerView;
    private Window window;
    MainActivity mainActivity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rewardsViewModel =
                ViewModelProviders.of(this).get(RewardsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        window=getActivity().getWindow();
        window.setStatusBarColor(Color.parseColor("#4A148C"));
        mainActivity= (MainActivity) getActivity();

        mainActivity.toolbar.setBackgroundColor(Color.parseColor("#880E4F"));


        rewardRecyclerView = view.findViewById(R.id.reward_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rewardRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CASH BACK", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("BUY 1 GET 1", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("DISCOUNT", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("CASH BACK", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("BUY 1 GET 1", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));
        rewardModelList.add(new RewardModel("DISCOUNT", "15th Mon JUN 2020", "GET 20% OFF CASH BACK on Any order Above LE.500 and LE.2500"));

        RewardAdapter adapter = new RewardAdapter(rewardModelList);
        rewardRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        window.setStatusBarColor(Color.parseColor("#00574B"));
        mainActivity.toolbar.setBackgroundColor(Color.parseColor("#008577"));

    }
}