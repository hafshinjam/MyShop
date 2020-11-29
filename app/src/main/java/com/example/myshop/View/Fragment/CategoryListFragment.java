package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Adapters.categoryAdapter;
import com.example.myshop.Model.Category;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {
    private categoryAdapter mCategoryAdapter;
    private RecyclerView mRecyclerView;
    private ProductRepository mRepository;
    private MutableLiveData<List<Category>> mCategoryListLive;

    public CategoryListFragment() {
        // Required empty public constructor
    }


    public static CategoryListFragment newInstance() {
        CategoryListFragment fragment = new CategoryListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = ProductRepository.getInstance();
        mRepository.fetchCategoriesList();
        setObservers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       setObservers();
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.list);
    }

    private void setObservers() {
        mCategoryListLive = new MutableLiveData<>();
        mCategoryListLive.observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                mCategoryListLive.setValue(categories);
                initAdapters();
            }
        });
    }

    private void initAdapters() {
        if (mCategoryAdapter == null) {
            mCategoryAdapter = new categoryAdapter(mCategoryListLive.getValue(), getActivity());
        }
        mRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.notifyDataSetChanged();
    }
}