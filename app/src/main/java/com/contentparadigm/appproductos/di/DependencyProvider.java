package com.contentparadigm.appproductos.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.contentparadigm.appproductos.data.products.ProductsRepository;
import com.contentparadigm.appproductos.data.products.datasource.cloud.CloudProductsDataSource;
import com.contentparadigm.appproductos.data.products.datasource.memory.MemoryProductsDataSource;

/**
 * Created by jorge.gutierrez on 27/04/2017.
 */

public class DependencyProvider {
    private static Context mContext;
    private static MemoryProductsDataSource memorySource = null;
    private static CloudProductsDataSource cloudSource = null;
    private static ProductsRepository mProductRepository = null;

    public DependencyProvider() {
    }

    public static ProductsRepository provideProductsRepository(@NonNull Context context) {
        mContext = context;
        if (mProductRepository == null) mProductRepository = new ProductsRepository(getMemorySource(),getCloudSource(),context);
        return mProductRepository;
    }

    public static MemoryProductsDataSource getMemorySource() {
        if (memorySource == null) memorySource = new MemoryProductsDataSource();
        return memorySource;
    }

    public static CloudProductsDataSource getCloudSource() {
        if (cloudSource == null) cloudSource = new CloudProductsDataSource();
        return cloudSource;
    }
}
