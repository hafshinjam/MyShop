package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Model.Product;
import com.example.myshop.repository.ProductRepository;

import java.util.List;

public class ProductListViewModel extends AndroidViewModel {
    private LiveData<List<Product>> mProductListLiveData;
    private ProductRepository mRepository;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mProductListLiveData=mRepository.getProductList();
    }

    public LiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }
}
