package com.example.myshop.Data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.myshop.Data.Model.Category;
import com.example.myshop.Data.database.CategoryDB;

import java.util.List;

public class CategoryDBRepository {
    private static CategoryDBRepository sCategoryDBRepository;
    private static Context mContext;
    private CategoryDB mDB;

    public static CategoryDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sCategoryDBRepository == null) {
            sCategoryDBRepository = new CategoryDBRepository();
        }
        return sCategoryDBRepository;
    }

    public CategoryDBRepository() {
        mDB = Room.databaseBuilder(mContext,
                CategoryDB.class,
                "categoryDB.db")
                .allowMainThreadQueries()
                .build();
    }

    public void insert(Category category) {
        mDB.CategoryDAO().insertCategory(category);
    }

    public void insertList(List<Category> categories) {
        Category[] list = new Category[categories.size()];
        list = categories.toArray(list);
        mDB.CategoryDAO().insertCategories(list);
    }

    public List<Category> getCategoryList() {
        return mDB.CategoryDAO().getCategories();
    }

    public List<Category> getParentCategories() {
        return getCategoryByParentCategory("0");
    }

    public List<Category> getCategoryByParentCategory(String parentID) {
        return mDB.CategoryDAO().getCategoriesByParent(parentID);
    }

    public Category getCategoryByID(String cateogryID) {
        return mDB.CategoryDAO().getCategoryByID(cateogryID);
    }
    public Category getCategoryByName(String name){
        return mDB.CategoryDAO().getCategoryByName(name);
    }

    public void update(Category category) {
        mDB.CategoryDAO().updateCategory(category);
    }

    public void delete(Category category) {
        mDB.CategoryDAO().deleteCategory(category);
    }

    public void clear() {
        mDB.CategoryDAO().clear();
    }

}
