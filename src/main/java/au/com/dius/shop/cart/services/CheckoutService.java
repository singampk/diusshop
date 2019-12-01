package au.com.dius.shop.cart.services;

import java.math.BigDecimal;

import au.com.dius.shop.cart.Engine;
import au.com.dius.shop.cart.Item;

/**
 * Checkout service is responsible to process the
 * checkout functions like scan, apply rules so on..
 */

public class CheckoutService {

    public CheckoutService(Engine engine) {

    }

    public void scan(Item item1) {

    }

    public BigDecimal total() {
        return new BigDecimal(0);
    }
}
