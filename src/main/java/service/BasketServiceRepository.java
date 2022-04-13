package service;

import entities.Basket;

public interface BasketServiceRepository {
    boolean saveOrder(Basket basket);
}
