package com.anas.mymall;

public class OrderItemModel {
    private int productImage;
    private String prductTitle;
    private String delivaryStatus;
    private int rating;

    public OrderItemModel(int productImage, int rating, String prductTitle, String delivaryStatus) {
        this.productImage = productImage;
        this.prductTitle = prductTitle;
        this.delivaryStatus = delivaryStatus;
        this.rating = rating;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getPrductTitle() {
        return prductTitle;
    }

    public void setPrductTitle(String prductTitle) {
        this.prductTitle = prductTitle;
    }

    public String getDelivaryStatus() {
        return delivaryStatus;
    }

    public void setDelivaryStatus(String delivaryStatus) {
        this.delivaryStatus = delivaryStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

