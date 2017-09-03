package com.contentparadigm.appproductos.data.products.datasource.cloud;

import android.os.Handler;

import com.contentparadigm.appproductos.products.domain.critera.ProductCriteria;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by jorge.gutierrez on 16/04/2017.
 */

public class CloudProductsDataSource implements ICloudProductsDataSource {
    private static HashMap<String, Product> API_DATA;
    private static final long LATENCY = 2000;

    static {
        API_DATA = new LinkedHashMap<>();
        for (int i = 0; i < 100; i++) addProduct(43, "Producto " + (i + 1), "file:///android_asset/mock-product.png");

    }

    private static void addProduct(float price, String name, String imageUrl){
        Product newProduct = new Product(price, name, imageUrl);
        API_DATA.put(newProduct.getCode(), newProduct);
    }
    @Override
    public void getProducts(final ProductServiceCallBack callback, ProductCriteria criteria) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(new ArrayList<Product>(API_DATA.values()));
            }
        }, LATENCY);
    }
}
