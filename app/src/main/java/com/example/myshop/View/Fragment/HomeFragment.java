package com.example.myshop.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myshop.View.Activity.ListActivity;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ImageView mMostViewedText;
    private ImageView mTopRatedText;
    private ImageView mRecentlyAddedText;
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
        mProductRepository = ProductRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(view);
        setOnclickListener();
        return view;
    }

    private void findViews(View view) {
        mMostViewedText = view.findViewById(R.id.mostViewed);
        mRecentlyAddedText = view.findViewById(R.id.newArrivals);
        mTopRatedText = view.findViewById(R.id.topRated);
    }

    private void setOnclickListener() {
        mTopRatedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductRepository.fetchProductListTopRated();
                showProductList();
            }
        });
        mRecentlyAddedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductRepository.fetchProductListRecent();
                showProductList();
            }
        });
        mMostViewedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductRepository.fetchProductListPopularity();
                showProductList();
            }
        });
    }

    private void showProductList() {
        Intent intent = ListActivity.newIntent(getActivity());
        startActivity(intent);
    }
}