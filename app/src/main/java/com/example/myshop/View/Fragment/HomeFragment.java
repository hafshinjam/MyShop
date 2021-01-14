package com.example.myshop.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myshop.R;
import com.example.myshop.View.Activity.ListActivity;
import com.example.myshop.databinding.FragmentHomeBinding;
import com.example.myshop.viewModel.HomeFragmentViewModel;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private HomeFragmentViewModel mHomeFragmentViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeFragmentViewModel = new ViewModelProvider(this)
                .get(HomeFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setViewModel(mHomeFragmentViewModel);
        setOnclickListener();
        return mBinding.getRoot();
    }

    private void setOnclickListener() {
        mBinding.topRated
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHomeFragmentViewModel.fetchProductListTopRated();
                        showProductList();
                        Log.d("click", "top");
                    }
                });
        mBinding.newArrivals
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHomeFragmentViewModel.fetchProductListRecent();
                        showProductList();
                        Log.d("click", "recent");
                    }
                });
        mBinding.mostViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeFragmentViewModel.fetchProductListPopularity();
                showProductList();
                Log.d("click", "popular");
            }
        });
        mBinding.searchButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mBinding.editTextProductHome.getText().toString();
                mHomeFragmentViewModel.setSearchParameter(search);
                mHomeFragmentViewModel.fetchProductListRecent();
                showProductList();
            }
        });
    }

    private void showProductList() {
        Intent intent = ListActivity.newIntent(getActivity());
        startActivity(intent);
    }
}