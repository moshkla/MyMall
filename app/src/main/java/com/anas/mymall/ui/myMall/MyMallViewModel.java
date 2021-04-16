package com.anas.mymall.ui.myMall;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyMallViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyMallViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is My Mall fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}