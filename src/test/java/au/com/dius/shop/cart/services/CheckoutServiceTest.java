package au.com.dius.shop.cart.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.dius.shop.cart.Cart;
import au.com.dius.shop.cart.Item;
import au.com.dius.shop.cart.engine.EngineContext;
import au.com.dius.shop.cart.engine.RulesEngine;

public class CheckoutServiceTest {

    private RulesEngine rulesEngine;
    private Item atv;
    private Item ipd;
    private Item mbp;
    private Item vga;


    @Before
    public void init() {
        rulesEngine = RulesEngine.getEngine();
        atv = StaticLoadItemsServiceImpl.getInstance().getItem("atv");
        vga = StaticLoadItemsServiceImpl.getInstance().getItem("vga");
        ipd = StaticLoadItemsServiceImpl.getInstance().getItem("ipd");
        mbp = StaticLoadItemsServiceImpl.getInstance().getItem("mbp");
    }

    /**
     * Test case to check deal offers
     */
    @Test
    public void test_checkout_deal_offer() {

        Map<String, List<Item>> cartItems = new HashMap<String, List<Item>>();

        Cart cart = new Cart()
                .withCartItems(cartItems);

        EngineContext ec = new EngineContext(rulesEngine, cart);
        CheckoutService cs = new CheckoutService(ec);

        cs.scan(atv);
        cs.scan(atv);
        cs.scan(atv);
        cs.scan(vga);

        Assert.assertEquals(new BigDecimal(249.00).setScale(2, RoundingMode.HALF_UP), cs.total());

    }

    /**
     * Test case to check deal offers with ipad bulk discount
     */
    @Test
    public void test_checkout_deal_offer_2() {

        Map<String, List<Item>> cartItems = new HashMap<String, List<Item>>();
        Cart cart = new Cart()
                .withCartItems(cartItems);

        EngineContext ec = new EngineContext(rulesEngine, cart);
        CheckoutService cs = new CheckoutService(ec);

        cs.scan(atv);
        cs.scan(ipd);
        cs.scan(ipd);
        cs.scan(atv);
        cs.scan(ipd);
        cs.scan(ipd);
        cs.scan(ipd);
        Assert.assertEquals(new BigDecimal(2718.95).setScale(2, RoundingMode.HALF_UP), cs.total());

    }

    /**
     * Test case to check deal offers with ipad bulk discount
     */
    @Test
    public void test_checkout_deal_offer_3() {

        Map<String, List<Item>> cartItems = new HashMap<String, List<Item>>();
        Cart cart = new Cart()
                .withCartItems(cartItems);

        EngineContext ec = new EngineContext(rulesEngine, cart);
        CheckoutService cs = new CheckoutService(ec);

        cs.scan(mbp);
        cs.scan(vga);
        cs.scan(ipd);

        Assert.assertEquals(new BigDecimal(1949.98).setScale(2, RoundingMode.HALF_UP), cs.total());

    }

    /**
     * Test case to check remove and rescan the sku
     */
    @Test
    public void test_checkout_when_an_item_is_removed() {

        Map<String, List<Item>> cartItems = new HashMap<String, List<Item>>();

        Cart cart = new Cart()
                .withCartItems(cartItems);

        EngineContext ec = new EngineContext(rulesEngine, cart);
        CheckoutService cs = new CheckoutService(ec);

        cs.scan(atv);
        cs.scan(atv);
        cs.scan(atv);
        cs.scan(vga);
        cs.scan(vga);
        cs.revertScan(vga);
        cs.scan(mbp);

        Assert.assertEquals(new BigDecimal(1618.99).setScale(2, RoundingMode.HALF_UP), cs.total());
    }
}
