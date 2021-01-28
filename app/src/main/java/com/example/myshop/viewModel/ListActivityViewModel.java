package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myshop.Data.repository.Repository;

public class ListActivityViewModel extends AndroidViewModel {
    Repository mRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
    }

    public void searchByMethod() {
        switch (mRepository.getSearchMethod()) {
            case "popular":
                searchWithPopularity();
                break;
            case "date":
                searchWithRecent();
                break;
            case "price":
                searchWithPrice();
            default:
                searchWithRecent();
        }
    }

    public void setSearchText(String searchText) {
        mRepository.setSearchText(searchText);
    }

    public void searchWithRecent() {
        mRepository.fetchProductListRecent(1);
    }

    public void searchWithPrice() {
        mRepository.fetchProductsListByPrice(1);
    }

    public void searchWithPopularity() {
        mRepository.fetchProductListPopularity(1);
    }
}
