package au.com.dius.shop.cart.rules;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import au.com.dius.shop.cart.Cart;
import au.com.dius.shop.cart.Item;
import au.com.dius.shop.cart.services.StaticLoadItemsServiceImpl;

public class Rules {

    /**
     * Actual rule evaluation functions, this a basic rules evaluator
     * with some hardcoded variables, however this same this can be achieved
     * by building new expression language processor engine.
     *
     * @param scannedItem Item scanned
     * @param items       Items that already scanned
     * @param expressions List of rules expressions
     * @param cart        Cart items
     * @return void
     */
    public void eval(Item scannedItem, List<Item> items, List<Expression> expressions, Cart cart) {
        for (Expression expression : expressions) {
            String exp = expression.getExpression();
            String[] words = exp.split("\\s+");
            processExp(scannedItem, words, items, expression, cart);
        }
    }


    private void processExp(Item scannedItem, String[] words, List<Item> items, Expression expression, Cart cart) {
        String item = words[0];
        StaticLoadItemsServiceImpl itemsService = StaticLoadItemsServiceImpl.getInstance();
        //if (items.contains(itemsService.getItem(item))) {
        if (scannedItem.getSku().equalsIgnoreCase(item) &&
                words[2].equalsIgnoreCase(Symbols._eq.getSymbol())) {
            if (Integer.parseInt(words[3]) == items.stream().filter(
                    v -> v.equals(scannedItem) && !v.isDiscountLock()).count()) {
                applyAction(expression, cart, scannedItem);
            }
        } else if (scannedItem.getSku().equalsIgnoreCase(item) &&
                words[2].equalsIgnoreCase(Symbols._graterthan.getSymbol())) {
            if (items.stream().filter(
                    v -> v.equals(scannedItem) && !v.isDiscountLock()).count() > Integer.parseInt(words[3])) {
                applyAction(expression, cart, scannedItem);
            }
        } else if (scannedItem.getSku().equalsIgnoreCase(item) &&
                words[2].equalsIgnoreCase(Symbols._lessthan.getSymbol())) {
            if (items.stream().filter(
                    v -> v.equals(scannedItem) && !v.isDiscountLock()).count() < Integer.parseInt(words[3])) {
                applyAction(expression, cart, scannedItem);
            }
        }
        //}
    }

    private void applyAction(Expression expression, Cart cart, Item scannedItem) {
        String action = expression.getAction();
        String[] words = action.trim().split("\\s+");
        if (words[0].equalsIgnoreCase(Symbols._discount.getSymbol())) {
            addDiscount(cart, scannedItem);
        } else if (words[0].equalsIgnoreCase(Symbols._newprice.getSymbol())) {
            addNewPrice(cart, scannedItem, words[1]);
        } else if (words[0].equalsIgnoreCase(Symbols._free.getSymbol())) {
            addFreeItem(cart, scannedItem, words[1]);
        }
    }

    private void addDiscount(Cart cart, Item item) {
        item.setDiscountedPrice(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
        List<Item> allApplicableItems = cart.getCartItemMap().get(item.getSku());
        allApplicableItems.forEach(i -> {
            i.setDiscountLock(true);
        });
    }

    private void addNewPrice(Cart cart, Item item, String newPrice) {
        List<Item> allApplicableItems = cart.getCartItemMap().get(item.getSku());
        allApplicableItems.forEach(i -> {
            i.setDiscountedPrice(new BigDecimal(newPrice).setScale(2, RoundingMode.HALF_UP));
            i.setDiscountLock(true);
        });
    }

    private void addFreeItem(Cart cart, Item item, String itemStr) {
        List<Item> allApplicableItems = cart.getCartItemMap().get(item.getSku());
        if (cart.getCartItemMap().get(itemStr) != null && !cart.getCartItemMap().get(itemStr).isEmpty()) {
            cart.getCartItemMap().get(itemStr).get(0).setDiscountedItem(true);
            cart.getCartItemMap().get(itemStr).get(0).setDiscountLock(true);
            cart.getCartItemMap().get(itemStr).get(0).setScanConfirmedRequired(true);
            cart.getCartItemMap().get(itemStr).get(0).setDiscountedPrice(new BigDecimal(0));
        } else {
            Item freeItem = StaticLoadItemsServiceImpl.getInstance().getItem(itemStr);
            freeItem.setScanConfirmedRequired(true);
            freeItem.setDiscountedPrice(new BigDecimal(0));
            cart.add(freeItem);
            allApplicableItems.forEach(i -> {
                i.setDiscountLock(true);
                i.setDiscountedItem(true);
            });
        }
    }
}
