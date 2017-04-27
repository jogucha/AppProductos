package com.contentparadigm.appproductos.data.products.datasource.memory;

import com.contentparadigm.appproductos.products.domain.critera.ProductCriteria;
import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 15/04/2017.
 */

public interface IMemoryProductsDataSource {
    List<Product> find(ProductCriteria criteria);
    void save(Product product);
    void deleteAll();
    boolean mapIsNull();
}
