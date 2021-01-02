package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myshop.R;
import com.example.myshop.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {
    private FragmentCartBinding mCartBinding;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCartBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_cart, container, false);
        mCartBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mCartBinding.getRoot();
    }
}