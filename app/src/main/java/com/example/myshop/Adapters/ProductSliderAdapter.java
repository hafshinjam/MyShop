package com.example.myshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myshop.Data.Model.Product;
import com.example.myshop.Data.repository.Repository;
import com.example.myshop.R;
import com.example.myshop.View.Activity.ProductViewActivity;
import com.example.myshop.databinding.SliderViewHolderBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ProductSliderAdapter extends SliderViewAdapter<ProductSliderAdapter.ProductSliderAdapterVH> {
    private Context mContext;
    List<Product> mProducts;
    Repository mRepository;

    public ProductSliderAdapter(Context context, List<Product> products) {
        mContext = context.getApplicationContext();
        mProducts = products;
        mRepository = Repository.getInstance(mContext);
    }

    @Override
    public ProductSliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SliderViewHolderBinding viewHolderBinding = SliderViewHolderBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new ProductSliderAdapterVH(viewHolderBinding);
    }

    @Override
    public void onBindViewHolder(ProductSliderAdapterVH viewHolder, int position) {
        Product product = mProducts.get(position);
        viewHolder.binding(product);
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    class ProductSliderAdapterVH extends SliderViewAdapter.ViewHolder {
        SliderViewHolderBinding mBinding;
        Product mProduct;

        public ProductSliderAdapterVH(SliderViewHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRepository.setProductToShow(mProduct);
                    Intent intent = ProductViewActivity.newIntent(mContext);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }

        public void binding(Product product) {
            Log.d("bind", product.getName());
            mProduct = product;
            mBinding.productNameSlider
                    .setText(mProduct.getName());
            if (product.getPhotoUriList().get(0) != null)
                Glide.with(mContext).load(product.getPhotoUriList().get(0)).placeholder(R.drawable.ic_product)
                        .into(mBinding.productPhotoSlider);
               /* Picasso.get()
                        .load(product.getPhotoUriList().get(0))
                        .placeholder(R.drawable.ic_product)
                        .into(mBinding.productPhotoSlider);*/
//            mBinding.executePendingBindings();
        }
    }
}
