package service;

import repositories.BoughtProductRepository;

public class BoughtProductService {

    private BoughtProductRepository boughtProductRepository;

    public BoughtProductService(BoughtProductRepository boughtProductRepository) {
        this.boughtProductRepository = boughtProductRepository;
    }


}
