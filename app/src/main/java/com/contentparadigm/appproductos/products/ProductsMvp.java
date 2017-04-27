package com.contentparadigm.appproductos.products;

import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 04/04/2017.
 */

public interface ProductsMvp {
    interface View {
        void showProducts(List<Product> products);
        void showLoadingState(boolean show);
        void showEmptyState();
        void showProductError(String msg);
        void showProductPage(List<Product> products);
        void showLoadMoreIndicator(boolean show);
        void allowMoreData(boolean show);
    }
    interface Presenter{
        void loadProducts(boolean reload);
    }
}
