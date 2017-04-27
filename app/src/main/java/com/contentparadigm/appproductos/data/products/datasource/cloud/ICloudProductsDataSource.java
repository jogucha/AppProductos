package com.contentparadigm.appproductos.data.products.datasource.cloud;

import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 16/04/2017.
 */

public interface ICloudProductsDataSource {
    interface ProductServiceCallBack {
        void onLoaded (List<Product> products);
        void onError (String error);

    }
    void getProducts (ProductServiceCallBack callback);
}
