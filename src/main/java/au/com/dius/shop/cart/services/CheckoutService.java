package au.com.dius.shop.cart.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import au.com.dius.shop.cart.Cart;
import au.com.dius.shop.cart.Item;
import au.com.dius.shop.cart.engine.EngineContext;
import au.com.dius.shop.cart.engine.RulesEngine;

/**
 * Checkout service is responsible to process the
 * checkout functions like scan, apply rules so on..
 */

public class CheckoutService {
    private EngineContext ec;

    public CheckoutService(EngineContext ec) {
        this.ec = ec;
    }

    /**
     * Scanning the item
     *
     * @param item
     */

    public void scan(Item item) {
        RulesEngine rulesEngine = ec.getEngine();
        if (validateScan(item)) {
            Item itemScanned = new Item(item);
            ec.getCart().add(itemScanned);
            rulesEngine.processEngine(ec, itemScanned);
        }
    }

    /**
     * Validate the scan to make sure the item been already
     * added to the cart
     *
     * @param item
     * @return
     */

    private boolean validateScan(Item item) {
        Cart cart = ec.getCart();
        List<Item> list = cart.getCartItemMap().get(item.getSku());
        if (list != null ) {
            List<Item> listScanConfirmed = list.stream().filter(Item::isScanConfirmedRequired)
                         .collect(Collectors.toList());
            if (!listScanConfirmed.isEmpty()) {
                listScanConfirmed.get(0).setScanConfirmedRequired(false);
                return false;
            }
        }
        return true;
    }


    /**
     * To get the total value of the cart
     *
     * @return
     */
    public BigDecimal total() {
        return ec.getCart().getTotal();
    }

    /**
     * Removing the item from the cart
     *
     * @param itemToBeRemoved
     */
    public void revertScan(Item itemToBeRemoved) {
        ec.getCart().removeItem(itemToBeRemoved);
        rescanAllItems();
    }

    /**
     * to rescan all the items in the cart
     */
    private void rescanAllItems() {
        Cart cart = new Cart();
        Cart oldCart = ec.getCart();
        ec.setCart(cart);
        oldCart.getAllItems().forEach(this::scan);
    }
}
