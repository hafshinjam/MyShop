package com.example.myshop.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;
import com.squareup.picasso.Picasso;


public class ProductViewFragment extends Fragment {
    private ProductRepository mProductRepository;
    private Product mProduct;
    private TextView mProductName;
    private TextView mProductDescription;
    private TextView mProductPrice;
    private ImageView mProductImage;
    private Button mAddToCartButton;


    public ProductViewFragment() {
        // Required empty public constructor
    }


    public static ProductViewFragment newInstance() {
        return new ProductViewFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductRepository = ProductRepository.getInstance();
        mProduct = mProductRepository.getProductToShow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_view, container, false);
        initViews(view);
        setListeners();
        return view;
    }

    private void initViews(View view) {
        mProductImage = view.findViewById(R.id.productImageView);
        Picasso.get().load(mProduct.getPhotoUriList().get(0))
                .placeholder(R.drawable.ic_product)
                .into(mProductImage);

        mProductName = view.findViewById(R.id.productNameTextView);
        mProductName.setText(mProduct.getName());

        mProductDescription = view.findViewById(R.id.productDescriptionTextView);
        mProductDescription.setText(mProduct.getDescription());

        mProductPrice = view.findViewById(R.id.productPriceTextView);
        mProductPrice.setText(String.valueOf(mProduct.getPrice()));
        mAddToCartButton = view.findViewById(R.id.addToCartButton);
    }

    private void setListeners() {
        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductRepository.addProductToCart(mProduct);
            }
        });
    }
}