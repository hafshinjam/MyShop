package com.example.myshop.Data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myshop.Data.Model.Category;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryDB extends RoomDatabase {
public abstract CategoryDatabaseDAO CategoryDAO();
}
