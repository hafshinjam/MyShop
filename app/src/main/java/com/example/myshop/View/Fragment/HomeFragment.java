package com.example.myshop.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Adapters.productAdapter;
import com.example.myshop.Data.Model.Product;
import com.example.myshop.R;
import com.example.myshop.View.Activity.ListActivity;
import com.example.myshop.databinding.FragmentHomeBinding;
import com.example.myshop.viewModel.HomeFragmentViewModel;

import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private HomeFragmentViewModel mHomeFragmentViewModel;
    private productAdapter mAdapter;

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
        mHomeFragmentViewModel.fetchSpecialProductList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setViewModel(mHomeFragmentViewModel);
        mBinding.specialProductList
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL, true));
        setOnclickListener();
        setObservers();
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

     /*   mBinding.searchButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mBinding.editTextProductHome.getText().toString();
                mHomeFragmentViewModel.setSearchParameter(search);
                mHomeFragmentViewModel.fetchProductListRecent();
                showProductList();
            }
        });*/
    }

    public void setObservers() {
        mHomeFragmentViewModel.getSpecialProductLive()
                .observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        initSpecialList(products);
                    }

                    private void initSpecialList(List<Product> products) {
                        if (mAdapter == null) {
                            mAdapter = new productAdapter(products, getContext());
                            mBinding.specialProductList.setAdapter(mAdapter);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    private void showProductList() {
        Intent intent = ListActivity.newIntent(getActivity());
        startActivity(intent);
    }
}