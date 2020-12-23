package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myshop.Adapters.productAdapter;
import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.databinding.FragmentListBinding;
import com.example.myshop.repository.ProductRepository;
import com.example.myshop.viewModel.ProductListViewModel;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends Fragment {
    private FragmentListBinding mBinding;
    private productAdapter mProductAdapter;
    //    private LiveData<List<Product>> mProductListLive;
    private ProductRepository mProductRepository;
    private ProductListViewModel mProductListViewModel;

    public ProductListFragment() {
        // Required empty public constructor
    }


    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductRepository = ProductRepository.getInstance();
        //    mProductListLive = mProductRepository.getProductList();
        mProductListViewModel = new ViewModelProvider(this)
                .get(ProductListViewModel.class);
        registerObservers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_list, container, false);
        mBinding.setViewModel(mProductListViewModel);
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mBinding.getRoot();
    }

    private void registerObservers() {
        mProductListViewModel
                .getProductListLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        initAdapter();
                        mBinding.list.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void initAdapter() {
        mProductAdapter = new productAdapter(mProductListViewModel
                .getProductListLiveData()
                .getValue()
                , getContext());
        mBinding
                .list
                .setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProductRepository.getProductList().setValue(new ArrayList<Product>());
    }
}