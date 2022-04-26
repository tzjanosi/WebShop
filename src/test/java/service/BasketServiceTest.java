package service;

import entities.Basket;
import entities.Product;
import entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    BasketServiceRepository basketRepository;

    @InjectMocks
    BasketService basketService;

    @Test
    @DisplayName("Test saving order at the end of buying")
    void saveOrderTest() {
        User user = new User("testuser@test.td", "testPassword");
        Product milk = new Product("milk", 250);
        Product bread = new Product("bread", 300);
        Basket basket = new Basket(user);
        basket.addProduct(milk, 5);
        basket.addProduct(bread, 2);
        when(basketRepository.saveOrder(basket)).thenReturn(true);
        assertThat(basketService.saveOrder(basket)).isTrue();
        verify(basketRepository).saveOrder(basket);
    }
}