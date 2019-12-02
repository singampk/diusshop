package au.com.dius.shop.cart.engine;

import au.com.dius.shop.cart.Cart;

/**
 * EngineContext will manage the state of the request
 */
public class EngineContext implements IEngineContext {

    private RulesEngine engine;
    private Cart cart;

    public EngineContext(RulesEngine e, Cart c) {
        this.cart = c;
        this.engine = e;
    }

    public Cart getCart() {
        return cart;
    }

    public RulesEngine getEngine() {
        return engine;
    }

    public void setCart(Cart cart) { this.cart = cart; }
}
