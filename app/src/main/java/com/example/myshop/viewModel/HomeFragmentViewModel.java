package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Data.Model.Product;
import com.example.myshop.Data.repository.ProductRepository;

import java.util.List;

public class HomeFragmentViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private LiveData<List<Product>> mSpecialProductLive;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance(getApplication());
        mSpecialProductLive = mProductRepository.getProductListSpecial();
    }

    public LiveData<List<Product>> getSpecialProductLive() {
        return mSpecialProductLive;
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

    public void fetchSpecialProductList() {
        mProductRepository.fetchSpecialProductsList(1);
    }

    public void setSearchParameter(String searchParameter) {
        mProductRepository.setSearchText(searchParameter);
    }

}
