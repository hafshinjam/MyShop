package com.example.myshop.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myshop.R;
import com.example.myshop.View.Activity.ListActivity;
import com.example.myshop.databinding.FragmentHomeBinding;
import com.example.myshop.repository.ProductRepository;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private ProductRepository mProductRepository;

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
        if (getArguments() != null) {
        }
        mProductRepository = ProductRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_home, container, false);
        setOnclickListener();
        return mBinding.getRoot();
    }

    private void setOnclickListener() {
        mBinding.topRated
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProductRepository.fetchProductListTopRated(1);
                        showProductList();
                    }
                });
        mBinding.newArrivals
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProductRepository.fetchProductListRecent(1);
                        showProductList();
                    }
                });
        mBinding.mostViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductRepository.fetchProductListPopularity(1);
                showProductList();
            }
        });
    }

    private void showProductList() {
        Intent intent = ListActivity.newIntent(getActivity());
        startActivity(intent);
    }
}