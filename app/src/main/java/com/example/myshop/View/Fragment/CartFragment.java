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

import com.example.myshop.Adapters.cartAdapter;
import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.databinding.FragmentCartBinding;
import com.example.myshop.viewModel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding mCartBinding;
    private CartViewModel mCartViewModel;
    private cartAdapter mAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartViewModel = new ViewModelProvider(this)
                .get(CartViewModel.class);
        registerListener();
        mCartViewModel.fetchCartList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCartBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_cart, container, false);
        mCartBinding.setViewModel(mCartViewModel);
        mCartBinding.cartList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mCartBinding.getRoot();
    }

    private void registerListener() {
        mCartViewModel.getCartListLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        initAdapter();
                    }
                });
    }

    private void initAdapter() {
        mAdapter = new cartAdapter(mCartViewModel.getCartListLiveData().getValue(), getContext());
        mCartBinding.cartList
                .setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}