package com.example.myshop.viewModel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Model.Product;
import com.example.myshop.repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class CartViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mCartListLiveData;

    public CartViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mCartListLiveData = mRepository.getProductsCart();
    }

    public LiveData<List<Product>> getCartListLiveData() {
        return mCartListLiveData;
    }

    public void fetchCartList() {
        SharedPreferences sharedPreferences = mRepository.getPreferences();
        Map<String, ?> listSharedPreferences = sharedPreferences.getAll();
        for (String id : listSharedPreferences.keySet()) {
            mRepository.fetchCartItem(id);
        }
    }
}
