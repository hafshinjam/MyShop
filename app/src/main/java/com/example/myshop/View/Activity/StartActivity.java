package com.example.myshop.View.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.myshop.Data.Model.Category;
import com.example.myshop.Data.repository.Repository;
import com.example.myshop.R;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    MutableLiveData<List<Category>> mCategoryLiveData;
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mRepository = Repository.getInstance(getApplicationContext());
        mCategoryLiveData = mRepository.getCategoriesList();
        mCategoryLiveData.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Intent intent = MainActivity.newIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
        mRepository.fetchCategoriesList();
    }
}