package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Data.Model.Product;
import com.example.myshop.Data.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private Repository mRepository;
    private LiveData<List<Product>> mCartListLiveData;
    private long totalPrice;
    private LiveData<Boolean> cartChangeLive;

    public CartViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
        mCartListLiveData = mRepository.getProductsCart();
        cartChangeLive = mRepository.getCartChange();
    }

    public LiveData<Boolean> getCartChangeLive() {
        return cartChangeLive;
    }

    public LiveData<List<Product>> getCartListLiveData() {
        return mCartListLiveData;
    }

    public void fetchCartList() {
        mRepository.fetchCartList();

    }

    public void calculateTotalPrice() {
        totalPrice = 0;
        if (mCartListLiveData.getValue() != null)
            for (Product product : mCartListLiveData.getValue()) {
                totalPrice += product.getPrice() * mRepository.getProductCartCount(product);
            }
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void EmptyList() {
        mRepository.setProductsCartValues(new ArrayList<>());
    }

    public boolean isListEmpty() {
        return mRepository.getProductCartSize() == 0;
    }
}
