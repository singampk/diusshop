package au.com.dius.shop.cart.rules;

import org.junit.Assert;
import org.junit.Test;

import au.com.dius.shop.cart.engine.RulesEngine;

public class RulesEngineTest {

    @Test
    public void load_rules_test() {
        RulesEngine engine = RulesEngine.getEngine();
        Assert.assertEquals(3, engine.getExpressions().size());
        Assert.assertEquals("atv size = 3, discount atv", engine.getExpressions().get(0).toString());
    }

    @Test
    public void validate_rules_test() {
        RulesEngine engine = RulesEngine.getEngine();
        Assert.assertTrue( engine.validateExpressions("atv size = 3, discount atv"));
        Assert.assertTrue( engine.validateExpressions("ipd size > 4, newprice 499.99"));
        Assert.assertTrue( engine.validateExpressions("mbp size = 1, free vga"));

    }
}
