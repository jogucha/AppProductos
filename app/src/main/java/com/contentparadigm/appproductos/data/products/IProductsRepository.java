package com.contentparadigm.appproductos.data.products;

import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 15/04/2017.
 */

public interface IProductsRepository {
    interface GetProductsCallBack{
        void onProductsLoaded(List<Product> products);
        void onDataNotAvailable(String error);
    }

    void getProducts(GetProductsCallBack callback);
    void refreshProducts();
}
