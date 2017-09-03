package com.contentparadigm.appproductos.data.products.datasource.memory;

import com.contentparadigm.appproductos.products.domain.critera.ProductCriteria;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by jorge.gutierrez on 15/04/2017.
 */

public class MemoryProductsDataSource implements IMemoryProductsDataSource {
    public static HashMap<String, Product> mCachedProducts;

    @Override
    public List<Product> find(ProductCriteria criteria) {
        ArrayList<Product> products = new ArrayList(mCachedProducts.values());
        return criteria.match(products);
    }

    @Override
    public void save(Product product) {
        if (mCachedProducts == null) mCachedProducts = new LinkedHashMap<>();
        mCachedProducts.put(product.getCode(), product);
    }

    @Override
    public void deleteAll() {
        if (mCachedProducts == null) mCachedProducts = new LinkedHashMap<>();
        mCachedProducts.clear();
    }

    @Override
    public boolean mapIsNull() {
        return mCachedProducts == null;
    }
}
