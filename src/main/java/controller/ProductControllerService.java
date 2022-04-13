package controller;

import entities.Product;

import java.util.List;

public interface ProductControllerService {

    List<Product> createListOfProducts();

    void insertMultipleProducts(List<Product> products);
}
