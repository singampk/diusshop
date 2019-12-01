package au.com.dius.shop.cart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.dius.shop.cart.services.CheckoutService;

public class CheckoutServiceTest {

    private Engine engine;

    @Before
    public void init() {
        RulesEngine rulesEngine = new RulesEngine();
        Engine engine = rulesEngine.createEngine();
    }

    /**
     * Test case to check deal offers
     */
    @Test
    public void test_checkout_deal_offer() {
        CheckoutService cs = new CheckoutService(engine);
        Item item1 = new Item();
        Item item2 = new Item();
        cs.scan(item1);
        cs.scan(item2);

        Assert.assertEquals(249,  cs.total());
    }
}
