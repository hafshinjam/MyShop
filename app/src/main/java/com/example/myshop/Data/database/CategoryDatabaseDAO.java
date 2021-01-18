package com.example.myshop.Data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myshop.Data.Model.Category;

import java.util.List;

@Dao
public interface CategoryDatabaseDAO {
    @Insert
    void insertCategory(Category category);

    @Insert
    void insertCategories(Category... categories);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("select * from CategoryTable")
    List<Category> getCategories();

    @Query("select * from CategoryTable where categoryID=:categoryID")
    Category getCategoryByID(String categoryID);

    @Query("select * from CategoryTable where categoryName=:name")
    Category getCategoryByName(String name);

    @Query("delete from CategoryTable")
    void clear();

    @Query("select * from CategoryTable where parentCategoryID=:parentID")
    List<Category> getCategoriesByParent(String parentID);
}
