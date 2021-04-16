package com.anas.mymall;

import java.util.List;

public class MyMallPageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_PANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;



    //-----------banner slider--------------
    private int type;
    private List<SliderModel> sliderModelList;

    public MyMallPageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //-----------banner slider--------------

    //-----------strip Ad--------------
    private String resource;
    private String backgroundColor;

    public MyMallPageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }


    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    //-----------strip Ad--------------
    //----------- Horizontal Product && GridView Product --------------
    private String title;
    private String bgTint;
    private List<HorizontalProductModel> horizontalProductModelList;
    private List<WishListModel> viewAllProductList;

    public MyMallPageModel(int type, String title, String bgTint, List<HorizontalProductModel> horizontalProductModelList, List<WishListModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.bgTint = bgTint;
        this.horizontalProductModelList = horizontalProductModelList;
        this.viewAllProductList = viewAllProductList;
    }

    public MyMallPageModel(int type, String title, String bgTint, List<HorizontalProductModel> horizontalProductModelList) {
        this.type = type;
        this.title = title;
        this.bgTint=bgTint;
        this.horizontalProductModelList = horizontalProductModelList;
    }

    public List<WishListModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishListModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    public String getBgTint() {
        return bgTint;
    }

    public void setBgTint(String bgTint) {
        this.bgTint = bgTint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalProductModel> getHorizontalProductModelList() {
        return horizontalProductModelList;
    }

    public void setHorizontalProductModelList(List<HorizontalProductModel> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }


//----------- Horizontal Product && GridView Product --------------



}
