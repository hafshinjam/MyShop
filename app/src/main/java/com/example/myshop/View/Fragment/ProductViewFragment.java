package com.example.myshop.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.viewModel.ProductViewModel;
import com.example.myshop.databinding.FragmentProductViewBinding;
import com.example.myshop.repository.ProductRepository;


public class ProductViewFragment extends Fragment {
    private ProductRepository mProductRepository;
    private Product mProduct;
   /* private TextView mProductName;
    private TextView mProductDescription;
    private TextView mProductPrice;
    private ImageView mProductImage;
    private Button mAddToCartButton;*/

    private FragmentProductViewBinding mProductViewBinding;
    public ProductViewModel mViewModel;


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
        mViewModel = new ViewModelProvider(this)
                .get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mProductViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_product_view, container, false);
        mProductViewBinding.setViewModel(mViewModel);
        mViewModel.setProductPicture(mProductViewBinding.productImageView);
      /*  initViews(view);
        setListeners();*/
        return mProductViewBinding.getRoot();
    }

  /*  private void initViews(View view) {
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
    }*/

   /* private void setListeners() {
        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductRepository.addProductToCart(mProduct);
            }
        });
    }*/
}