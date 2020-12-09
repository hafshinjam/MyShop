package com.example.myshop.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {
    private RecyclerView mProductRecyclerView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        return view;

    }

    private void findViews(View view) {
        mProductRecyclerView = view.findViewById(R.id.list);
    }
}