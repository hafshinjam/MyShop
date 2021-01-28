package com.example.myshop.Data.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "CategoryTable")
public class Category {
    @PrimaryKey
    @NonNull
    private String categoryID;
    @ColumnInfo(name = "parentCategoryID")
    private String parentCategoryID;
    @ColumnInfo(name = "categoryName")
    private String categoryName;
    @ColumnInfo(name = "categoryPhotoURL")
    private String photoUri;

    public Category(String categoryName, String categoryID, String photoUri, String parentCategoryID) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.photoUri = photoUri;
        this.parentCategoryID = parentCategoryID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(String parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryID.equals(category.categoryID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID);
    }
}
