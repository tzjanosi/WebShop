package repositories;

import entities.Basket;
import service.BasketServiceRepository;

public class BasketRepository implements BasketServiceRepository {

    @Override
    public boolean saveOrder(Basket basket) {
        return false;
    }
}
