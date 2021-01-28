package com.example.myshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myshop.Data.repository.Repository;

public class MainActivityViewModel extends AndroidViewModel {
    Repository mRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
    }

    public void setSearchText(String searchText) {
        mRepository.setSearchText(searchText);
    }

    public void searchWithRecent() {
        mRepository.fetchProductListRecent(1);
    }
}
