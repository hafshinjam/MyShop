package com.example.myshop.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Adapters.ProductSliderAdapter;
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
    private ProductSliderAdapter mSliderAdapter;
    private productAdapter mAdapterMostViewed;
    private productAdapter mAdapterMostRecent;
    private productAdapter mAdapterTopRated;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeFragmentViewModel = new ViewModelProvider(this)
                .get(HomeFragmentViewModel.class);
        mHomeFragmentViewModel.fetchSpecialProductList();
        mHomeFragmentViewModel.fetchLiveDataMostViewed();
        mHomeFragmentViewModel.fetchLiveDataRate();
        mHomeFragmentViewModel.fetchLiveDataRecent();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setViewModel(mHomeFragmentViewModel);
        initViews();
        setOnclickListener();
        setObservers();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.mostViewedList
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL, true));
        mBinding.newArrivalList
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL, true));
        mBinding.topRatedList
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL, true));

    }

    private void setOnclickListener() {
        mBinding.topRatedTextButton
                .setOnClickListener(v -> {
                    mHomeFragmentViewModel.fetchProductListTopRated();
                    showProductList();
                    Log.d("click", "top");
                });
        mBinding.newArrivalTextButton
                .setOnClickListener(v -> {
                    mHomeFragmentViewModel.fetchProductListRecent();
                    showProductList();
                    Log.d("click", "recent");
                });
        mBinding.mostViewedTextButton.setOnClickListener(v -> {
            mHomeFragmentViewModel.fetchProductListPopularity();
            showProductList();
            Log.d("click", "popular");
        });
    }

    public void setObservers() {
        mHomeFragmentViewModel.getSpecialProductLive()
                .observe(getViewLifecycleOwner(), products -> {
                    if (getContext() != null) {
                        mSliderAdapter = new ProductSliderAdapter(getContext(), products);
                        mBinding.specialProductSlider.setSliderAdapter(mSliderAdapter);

                        mSliderAdapter.notifyDataSetChanged();
                        mBinding.specialProductSlider.startAutoCycle();
                    }
                });
        mHomeFragmentViewModel.getProductLiveByRate()
                .observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        mAdapterTopRated = new productAdapter(products, getContext());
                        mBinding.topRatedList.setAdapter(mAdapterTopRated);
                        mAdapterTopRated.notifyDataSetChanged();
                    }
                });
        mHomeFragmentViewModel.getProductsLivePopular()
                .observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        mAdapterMostViewed = new productAdapter(products, getContext());
                        mBinding.mostViewedList.setAdapter(mAdapterMostViewed);
                        mAdapterMostViewed.notifyDataSetChanged();
                    }
                });
        mHomeFragmentViewModel.getProductsHomeRecent()
                .observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        mAdapterMostRecent = new productAdapter(products, getContext());
                        mBinding.newArrivalList.setAdapter(mAdapterMostRecent);
                        mAdapterMostRecent.notifyDataSetChanged();
                    }
                });
    }


    private void showProductList() {
        Intent intent = ListActivity.newIntent(getActivity());
        startActivity(intent);
    }
}