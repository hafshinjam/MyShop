package com.example.myshop.Model;

public class Category {
    private String mCategoryID;
    private String mCategoryName;
    private String mPhotoUri;

    public Category(String categoryName, String categoryID, String photoUri) {
        mCategoryName = categoryName;
        mCategoryID = categoryID;
        mPhotoUri = photoUri;
    }

    public String getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(String categoryID) {
        mCategoryID = categoryID;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }
}
