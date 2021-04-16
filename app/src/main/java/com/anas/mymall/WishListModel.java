package com.anas.mymall;

public class WishListModel {
    private String productId;
    private String productimage;
    private String productName;
    private long freeCoupons;
    private String ratings;
    private long totalRatings;
    private String productPrice;
    private String cuttedPrice;
    private boolean COD;

    public WishListModel(String productId,String productimage, String productName, long freeCoupons, String ratings, long totalRatings, String productPrice, String cuttedPrice, boolean COD) {
        this.productId=productId;
        this.productimage = productimage;
        this.productName = productName;
        this.freeCoupons = freeCoupons;
        this.ratings = ratings;
        this.totalRatings = totalRatings;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.COD = COD;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(long freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public boolean getCOD() {
        return COD;
    }

    public void setCOD(boolean paymentMethod) {
        this.COD = paymentMethod;
    }
}
