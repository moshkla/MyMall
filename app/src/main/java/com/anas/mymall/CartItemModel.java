package com.anas.mymall;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUBT = 1;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //---------------Cart Item------------------
    private String productID;
    private String productImage;
    private String productTitle;
    private long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private long productQuantity;
    private long offersApplied;
    private long coupensApplied;




    public CartItemModel(int type,String productID, String productImage, String productTitle, long freeCoupens, String productPrice, String cuttedPrice, long productQuantity, long offersApplied, long coupensApplied) {
        this.type = type;
        this.productID=productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(int freeCoupens) {
        this.freeCoupens = freeCoupens;
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

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }
    //---------------Cart Item------------------


    //---------------Cart Total Amount------------------
    private String totalAmount;
    private String totalItems;
    private String totalItemPrice;
    private String delivaryPrice;
    private String saveAmount;

    public CartItemModel(int type, String totalItems,String totalAmount, String totalItemPrice, String delivaryPrice, String saveAmount) {
        this.totalAmount=totalAmount;
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.delivaryPrice = delivaryPrice;
        this.saveAmount = saveAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDelivaryPrice() {
        return delivaryPrice;
    }

    public void setDelivaryPrice(String delivaryPrice) {
        this.delivaryPrice = delivaryPrice;
    }

    public String getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(String saveAmount) {
        this.saveAmount = saveAmount;
    }
    //---------------Cart Total Amount------------------


}
