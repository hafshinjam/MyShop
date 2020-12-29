package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Model.Product;
import com.example.myshop.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListViewModel extends AndroidViewModel {
    private LiveData<List<Product>> mProductListLiveData;
    private ProductRepository mRepository;
    Map<String, String> SortOrder;
    Map<String, String> SortMethod;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mProductListLiveData = mRepository.getProductList();
        SortOrder = new HashMap<>();
        SortMethod = new HashMap<>();
    }

    public LiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public void setSortModel(String sortMethod) {
        SortMethod.put("orderby", sortMethod);
        mRepository.setSortMethod(SortMethod);
    }

    public void setSortOrder(Boolean sortOrder) {
        if (sortOrder)
            SortOrder.put("order", "desc");
        else
            SortOrder.put("order", "asc");
        mRepository.setSortOrder(SortOrder);
    }

    public void fetchProductsSortPrice() {
    }

    public void fetchProductsSortDate() {

    }

    public void fetchProductsSortPopularity() {

    }
}
