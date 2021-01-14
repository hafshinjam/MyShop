package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myshop.repository.ProductRepository;

public class HomeFragmentViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance(getApplication());
    }

    public void fetchProductListTopRated() {
        mProductRepository.fetchProductListTopRated(1);
    }

    public void fetchProductListRecent() {
        mProductRepository.fetchProductListRecent(1);
    }

    public void fetchProductListPopularity() {
        mProductRepository.fetchProductListPopularity(1);
    }

    public void setSearchParameter(String searchParameter) {
        mProductRepository.setSearchText(searchParameter);
    }
}
