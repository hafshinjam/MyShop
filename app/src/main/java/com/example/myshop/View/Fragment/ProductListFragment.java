package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myshop.Adapters.productAdapter;
import com.example.myshop.Data.Model.Product;
import com.example.myshop.R;
import com.example.myshop.databinding.FragmentListBinding;
import com.example.myshop.viewModel.ProductListViewModel;

import java.util.List;


public class ProductListFragment extends Fragment {
    private FragmentListBinding mBinding;
    private productAdapter mProductAdapter;
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
        mProductListViewModel = new ViewModelProvider(this)
                .get(ProductListViewModel.class);
        registerObservers();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_list, container, false);
        mBinding.setViewModel(mProductListViewModel);
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        initViews();
        setListeners();
        return mBinding.getRoot();
    }

    private void initViews() {
        ArrayAdapter<CharSequence> sortMethod =
                ArrayAdapter.
                        createFromResource(getActivity(), R.array.sortMethod,
                                android.R.layout.simple_spinner_item);
        sortMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding
                .SortSpinner
                .setAdapter(sortMethod);
        ArrayAdapter<CharSequence> sortOrder =
                ArrayAdapter.
                        createFromResource(getActivity(), R.array.sortOrder,
                                android.R.layout.simple_spinner_item);
        sortMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding
                .AscDscSpinner
                .setAdapter(sortOrder);
    }

    private void setListeners() {
        mBinding.SortSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 1:
                                mProductListViewModel.fetchProductsSortDate();
                                break;
                            case 2:
                                mProductListViewModel.fetchProductsSortPrice();
                                break;
                            case 3:
                                mProductListViewModel.fetchProductsSortPopularity();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mBinding.AscDscSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        boolean order;
                        order = position == 0;
                        int method = (int) mBinding.SortSpinner.getSelectedItemId();
                        mProductListViewModel.setSortOrderAndSearch(order,method);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mBinding.buttonSearchProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchParameter = mBinding.editTextProductList.getText().toString();
                mProductListViewModel.search(searchParameter);
            }
        });
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
                .getProductList()
                , getContext());
        mBinding
                .list
                .setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        mProductListViewModel.getProductListLiveData().removeObservers(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProductListViewModel.setDefaultOnDestroy();

    }
}