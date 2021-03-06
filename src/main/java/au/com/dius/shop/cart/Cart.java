package au.com.dius.shop.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {

    private Map<String, List<Item>> cartItemMap;

    public Cart() {
        cartItemMap = new HashMap<String, List<Item>>();
    }

    public Map<String, List<Item>> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<String, List<Item>> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }


    /**
     * To add a default cart
     *
     * @param cartItems
     * @return
     */
    public Cart withCartItems(Map<String, List<Item>> cartItems) {
        setCartItemMap(cartItems);
        return this;
    }

    /**
     * To get all the items in the cart as list
     *
     * @return list of cart items
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        for (List<Item> list : getCartItemMap().values()) {
            items.addAll(list);
        }
        return items;
    }

    /**
     * To add item to the cart
     *
     * @param item
     */
    public void add(Item item) {
        if (!getCartItemMap().containsKey(item.getSku())) {
            getCartItemMap().put(item.getSku(), new ArrayList<Item>());
        }
        getCartItemMap().get(item.getSku()).add(item);
    }

    /**
     * To get the total of the cart
     *
     * @return BigDeimal total value
     */
    public BigDecimal getTotal() {
        return new BigDecimal(getAllItems().stream().mapToDouble(i -> i.getPrice().doubleValue()).sum())
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Removing an item from the cart
     *
     * @param itemToBeRemoved
     */
    public void removeItem(Item itemToBeRemoved) {
        List allSkuItems = getCartItemMap().get(itemToBeRemoved.getSku());
        if (allSkuItems.size() > 0)
            allSkuItems.remove(allSkuItems.size() - 1);
        resetAllPrices();
    }

    /**
     * Resetting all the prices on a removal of an item
     */
    private void resetAllPrices() {
        getAllItems().forEach((Item item) -> {
            item.setDiscountedPrice(new BigDecimal(-1));
            item.setDiscountLock(false);
        });
        List<Item> itemsToRemove = getAllItems()
                .stream()
                .filter(Item::isDiscountedItem).collect(Collectors.toList());
        for (Item toRemove : itemsToRemove) {
            List<Item> ls = getCartItemMap().get(toRemove.getSku());
            if (!ls.isEmpty()) {
                ls.remove(ls.size() - 1);
            }
        }
    }
}
