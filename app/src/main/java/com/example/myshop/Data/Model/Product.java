package com.example.myshop.Data.Model;

import java.util.List;

public class Product {
    private String mId;
    private String mName;
    private String mPermalink;
    private String mDescription;
    private long mPrice;
    private List<String> mPhotoUriList;
    private boolean mIsInStock;

    public Product() {
    }

    public Product(String id, String name,
                   String permalink,
                   String description,
                   long price,
                   List<String> photoUriList,
                   boolean isInStock
    ) {
        mId = id;
        mName = name;
        mPermalink = permalink;
        mDescription = description;
        mPrice = price;
        mPhotoUriList = photoUriList;
        mIsInStock = isInStock;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        mPermalink = permalink;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getPrice() {
        return mPrice;
    }

    public void setPrice(long price) {
        mPrice = price;
    }

    public List<String> getPhotoUriList() {
        return mPhotoUriList;
    }

    public void setPhotoUriList(List<String> photoUriList) {
        mPhotoUriList = photoUriList;
    }

    public boolean isInStock() {
        return mIsInStock;
    }

    public void setInStock(boolean inStock) {
        mIsInStock = inStock;
    }
}
