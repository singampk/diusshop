package au.com.dius.shop.cart.services;

import java.util.Map;

import au.com.dius.shop.cart.Item;

public interface LoadItemsService {
    public Map<String, Item> loadItems();
}
