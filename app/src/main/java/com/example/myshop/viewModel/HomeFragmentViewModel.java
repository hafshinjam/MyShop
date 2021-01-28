package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myshop.Data.Model.Product;
import com.example.myshop.Data.repository.Repository;

import java.util.List;

public class HomeFragmentViewModel extends AndroidViewModel {
    private Repository mRepository;
    private LiveData<List<Product>> mSpecialProductLive;
    private LiveData<List<Product>> mProductsHomeRecent;
    private LiveData<List<Product>> mProductsLivePopular;
    private LiveData<List<Product>> mProductLiveByRate;


    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
        mSpecialProductLive = mRepository.getProductListSpecial();
        mProductLiveByRate = mRepository.getProductsByRate();
        mProductsHomeRecent = mRepository.getProductsRecent();
        mProductsLivePopular = mRepository.getProductsByPopularity();
    }

    public LiveData<List<Product>> getSpecialProductLive() {
        return mSpecialProductLive;
    }

    public LiveData<List<Product>> getProductsHomeRecent() {
        return mProductsHomeRecent;
    }

    public LiveData<List<Product>> getProductsLivePopular() {
        return mProductsLivePopular;
    }

    public LiveData<List<Product>> getProductLiveByRate() {
        return mProductLiveByRate;
    }

    public void fetchLiveDataRate() {
        mRepository.fetchByRatingHome();
    }

    public void fetchLiveDataRecent() {
        mRepository.fetchRecentHome();
    }

    public void fetchLiveDataMostViewed() {
        mRepository.fetchByPopularityHome();
    }

    public void fetchProductListTopRated() {
        mRepository.fetchProductListTopRated(1);
    }

    public void fetchProductListRecent() {
        mRepository.fetchProductListRecent(1);
    }

    public void fetchProductListPopularity() {
        mRepository.fetchProductListPopularity(1);
    }

    public void fetchSpecialProductList() {
        mRepository.fetchSpecialProductsList(1);
    }

    public void setSearchParameter(String searchParameter) {
        mRepository.setSearchText(searchParameter);
    }

}
