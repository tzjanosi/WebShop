package service;

import controller.BasketControllerService;
import entities.Basket;
import repositories.BasketRepository;

public class BasketService implements BasketControllerService {

    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public boolean saveOrder(Basket basket) {
        return false;
    }
}
