package com.contentparadigm.appproductos.products.domain.critera;

import com.contentparadigm.appproductos.products.domain.model.Product;

import java.util.List;

/**
 * Created by jorge.gutierrez on 15/04/2017.
 */

public interface ProductCriteria {
    List<Product> match(List<Product> products);
}
