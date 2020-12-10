package com.example.myshop.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.Adapters.productAdapter;
import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {
    private RecyclerView mProductRecyclerView;
    private productAdapter mProductAdapter;
    private LiveData<List<Product>> mProductListLive;
    private ProductRepository mProductRepository;

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
        mProductListLive = mProductRepository.getProductList();
        registerObservers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }

    private void findViews(View view) {
        mProductRecyclerView = view.findViewById(R.id.list);
    }

    private void registerObservers() {
        mProductListLive.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                initAdapter(products);
            }
        });
    }

    private void initAdapter(List<Product> products) {
        if (mProductAdapter == null) {
            mProductAdapter = new productAdapter(products, getContext());
            mProductRecyclerView.setAdapter(mProductAdapter);
        }
        mProductAdapter.notifyDataSetChanged();
    }
}