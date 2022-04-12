package service;

import repositories.BasketRepository;

public class BasketService {

    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }


}
