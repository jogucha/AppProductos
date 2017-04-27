package com.contentparadigm.appproductos.data.products;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.contentparadigm.appproductos.data.products.datasource.cloud.ICloudProductsDataSource;
import com.contentparadigm.appproductos.data.products.datasource.memory.IMemoryProductsDataSource;
import com.contentparadigm.appproductos.products.domain.critera.ProductCriteria;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 15/04/2017.
 */

public class ProductsRepository implements IProductsRepository {
    private final IMemoryProductsDataSource mMemoryProductsDataSource;
    private final ICloudProductsDataSource mCloudProductsDataSource;
    private final Context mContext;

    private boolean mReload;

    public ProductsRepository(IMemoryProductsDataSource mMemoryProductsDataSource,
                              ICloudProductsDataSource mCloudProductsDataSource,
                              Context mContext) {
        this.mMemoryProductsDataSource = mMemoryProductsDataSource;
        this.mCloudProductsDataSource = mCloudProductsDataSource;
        this.mContext = mContext;
    }

    @Override
    public void getProducts(final GetProductsCallBack callback) {
        if (!mMemoryProductsDataSource.mapIsNull() && !mReload){
            getProductsFromMemory(callback);
            return;
        }
        if (mReload){
            getProductsFromServer(callback);
        }
        else {
            List<Product> products = mMemoryProductsDataSource.find(new ProductCriteria() {
                @Override
                public List<Product> match(List<Product> products) {
                    return null;
                }
            });
            if (products.size() > 0) callback.onProductsLoaded(products);
            else getProductsFromServer(callback);
        }

    }

    @Override
    public void refreshProducts() {
        mReload = true;
    }

    private void getProductsFromMemory (GetProductsCallBack callback){
        callback.onProductsLoaded(mMemoryProductsDataSource.find(new ProductCriteria() {
            @Override
            public List<Product> match(List<Product> products) {
                return null;
            }
        }));
    }
    private void getProductsFromServer (final GetProductsCallBack callback) {
        if (!isOnline()) callback.onDataNotAvailable("No hay conexión de Red.");
        mCloudProductsDataSource.getProducts(new ICloudProductsDataSource.ProductServiceCallBack(){
            @Override
            public void onLoaded(List<Product> products) {
                refreshMemoryDataSource(products);
                getProductsFromMemory(callback);
            }

            @Override
            public void onError(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    private boolean isOnline () {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void refreshMemoryDataSource (List<Product> products) {
        mMemoryProductsDataSource.deleteAll();
        for (Product product:products) mMemoryProductsDataSource.save(product);
        mReload = false;
    }
}
