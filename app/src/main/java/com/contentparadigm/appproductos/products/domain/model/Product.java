package com.contentparadigm.appproductos.products.domain.model;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by jorge.gutierrez on 27/03/2017.
 */

public class Product {
    private String mCode;
    private String mName;
    private String mDescription;
    private String mBrand;
    private float mPrice;
    private int mUnitsInStock;
    private String mImageUrl;

    public Product(float mPrice, String mName, String mImageUrl) {
        this.mCode = UUID.randomUUID().toString();
        this.mName = mName;
        this.mPrice = mPrice;
        this.mImageUrl = mImageUrl;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public int getUnitsInStock() {
        return mUnitsInStock;
    }

    public void setUnitsInStock(int mUnitsInStock) {
        this.mUnitsInStock = mUnitsInStock;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getFormatedPrice(){
        return String.format("$%s", mPrice);
    }

    public String getFormattedUnitsInStock() {
        return String.format(Locale.getDefault(), "%d u", mUnitsInStock);
    }
}
