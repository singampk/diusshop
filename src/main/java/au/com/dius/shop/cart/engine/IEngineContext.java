package au.com.dius.shop.cart.engine;

import au.com.dius.shop.cart.Cart;

/**
 * This Engine context will keep the state of the request
 */
public interface IEngineContext {

    public Cart getCart();
    public RulesEngine getEngine();
}
