package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Data.Model.Product;
import com.example.myshop.Data.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListViewModel extends AndroidViewModel {
    private LiveData<List<Product>> mProductListLiveData;
    private Repository mRepository;
    Map<String, String> SortOrder;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
        mProductListLiveData = mRepository.getProductList();
        SortOrder = new HashMap<>();
    }

    public LiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public List<Product> getProductList() {
        return mProductListLiveData.getValue();
    }

    public void setSortOrderAndSearch(Boolean sortOrder, int method) {
        if (sortOrder)
            SortOrder.put("order", "desc");
        else
            SortOrder.put("order", "asc");
        mRepository.setSortOrder(SortOrder);
        switch (method) {
            case 1:
                mRepository.setSearchMethod("popular");
                fetchProductsSortPopularity();
                break;
            case 2:
                mRepository.setSearchMethod("price");
                fetchProductsSortPrice();
                break;
            case 3:
                mRepository.setSearchMethod("date");
                fetchProductsSortDate();
        }
    }

    public void search(String searchParameter) {
        mRepository.setSearchText(searchParameter);
        mRepository.fetchProductListRecent(1);
    }

    public void fetchProductsSortPrice() {
        mRepository.fetchProductsListByPrice(1);
    }

    public void fetchProductsSortDate() {
        mRepository.fetchProductListRecent(1);
    }

    public void fetchProductsSortPopularity() {
        mRepository.fetchProductListPopularity(1);
    }

    public void setDefaultOnDestroy() {
        mRepository
                .setCategoryID(null);
        mRepository
                .setSearchText(null);
        mRepository
                .getProductList()
                .setValue(new ArrayList<Product>());
        mRepository.setSearchMethod("date");
    }
}
