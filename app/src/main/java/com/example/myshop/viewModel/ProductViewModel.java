package com.example.myshop.viewModel;

import android.app.Application;
import android.os.Build;
import android.text.Html;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myshop.Model.Product;
import com.example.myshop.R;
import com.example.myshop.repository.ProductRepository;
import com.squareup.picasso.Picasso;

public class ProductViewModel extends AndroidViewModel {
    private Product mProduct;
    private ProductRepository mProductRepository;
    private int mProductCount = 0;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance(getApplication());
        mProduct = mProductRepository.getProductToShow();
        mProductCount = mProductRepository.getProductCartCount(mProduct);
    }

    public void incrementCount() {
        mProductRepository.updateProductToCart(mProduct, ++mProductCount);
    }

    public void decrementCount() {
        if (mProductCount > 0)
            mProductRepository.updateProductToCart(mProduct, --mProductCount);
    }


    public String getProductTitle() {
        return mProduct.getName();
    }

    public String getProductDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mProduct.getDescription(), Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(mProduct.getDescription()).toString();
        }
    }

    public String getProductPrice() {
        return String.valueOf(mProduct.getPrice());
    }

    public void setProductPicture(ImageView productPicture) {
        Picasso.get()
                .load(mProduct.getPhotoUriList().get(0))
                .placeholder(R.drawable.ic_product)
                .into(productPicture);
    }

    public int getProductCount() {
        return mProductCount;
    }
}
