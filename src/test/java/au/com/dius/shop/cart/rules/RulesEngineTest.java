package au.com.dius.shop.cart.rules;

import org.junit.Assert;
import org.junit.Test;

import au.com.dius.shop.cart.Engine;

public class RulesEngineTest {

    @Test
    public void load_rules_test() {
        RulesEngine engine = new RulesEngine();
        Assert.assertEquals(3, engine.getExpressions().size());
    }
}
