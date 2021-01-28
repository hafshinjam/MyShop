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
import com.example.myshop.Data.Model.Product;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCartBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_cart, container, false);
        mCartBinding.setViewModel(mCartViewModel);
        if (mCartViewModel.isListEmpty()) {
            mCartBinding.emptyCartPic.setVisibility(View.VISIBLE);
            mCartBinding.cartList.setVisibility(View.INVISIBLE);
        }
        mCartBinding.cartList.setLayoutManager(new LinearLayoutManager(getActivity()));
        initViews();
        return mCartBinding.getRoot();
    }

    private void initViews() {
        mCartBinding.TotalPrice.setText("0");
    }

    private void registerListener() {
        mCartViewModel.getCartListLiveData()
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        if (products.size() == 0)
                            mCartBinding.TotalPrice.setText(String.valueOf(0));
                        initAdapter();
                    }
                });
        mCartViewModel.getCartChangeLive()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        mCartViewModel.calculateTotalPrice();
                        mCartBinding.TotalPrice.
                                setText(String.valueOf(mCartViewModel.getTotalPrice()));
                        if (mCartViewModel.isListEmpty()) {
                            mCartBinding.cartList.setVisibility(View.INVISIBLE);
                            mCartBinding.emptyCartPic.setVisibility(View.VISIBLE);
                        } else {
                            mCartBinding.cartList.setVisibility(View.VISIBLE);
                            mCartBinding.emptyCartPic.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void initAdapter() {
        mCartBinding.emptyCartPic.setVisibility(View.INVISIBLE);
        mCartBinding.cartList.setVisibility(View.VISIBLE);
        mAdapter = new cartAdapter(mCartViewModel.getCartListLiveData().getValue(), getContext());
        mCartBinding.cartList
                .setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mCartViewModel.calculateTotalPrice();
        mCartBinding.TotalPrice.setText(String.valueOf(mCartViewModel.getTotalPrice()));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCartViewModel.isListEmpty()) {
            mCartBinding.emptyCartPic.setVisibility(View.VISIBLE);
            mCartBinding.cartList.setVisibility(View.INVISIBLE);
        }
        mCartViewModel.fetchCartList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCartViewModel.EmptyList();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCartViewModel.EmptyList();
    }
}