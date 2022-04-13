package service;

import controller.BasketControllerService;
import entities.Basket;

public class BasketService implements BasketControllerService {

    private BasketServiceRepository basketServiceRepository;

    public BasketService(BasketServiceRepository basketServiceRepository) {
        this.basketServiceRepository = basketServiceRepository;
    }

    @Override
    public boolean saveOrder(Basket basket) {
        return basketServiceRepository.saveOrder(basket);
    }
}
