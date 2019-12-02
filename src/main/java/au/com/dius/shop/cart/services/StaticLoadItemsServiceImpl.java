package au.com.dius.shop.cart.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import au.com.dius.shop.cart.Item;

/**
 * This service is one the implementations to load
 * the items hard coded
 */

public class StaticLoadItemsServiceImpl implements LoadItemsService {

    private static StaticLoadItemsServiceImpl staticLoadItemsService;

    private StaticLoadItemsServiceImpl() {

    }

    public static StaticLoadItemsServiceImpl getInstance() {
        if (staticLoadItemsService == null) {
            staticLoadItemsService = new StaticLoadItemsServiceImpl();
        }
        staticLoadItemsService.loadItems();
        return staticLoadItemsService;
    }

    private Map<String, Item> items = new HashMap<String, Item>();

    //        ipd	Super iPad	$549.99
    //        mbp	MacBook Pro	$1399.99
    //        atv	Apple TV	$109.50
    //        vga	VGA adapter	$30.00

    public Map<String, Item> loadItems() {
        items.put("ipd", new Item("Super iPad", "ipd", new BigDecimal(549.99).setScale(2, RoundingMode.HALF_UP)));
        items.put("mbp", new Item("MacBook Pro", "mbp", new BigDecimal(1399.99).setScale(2, RoundingMode.HALF_UP)));
        items.put("atv", new Item("Apple TV", "atv", new BigDecimal(109.50).setScale(2, RoundingMode.HALF_UP)));
        items.put("vga", new Item("VGA adapter", "vga", new BigDecimal(30.00).setScale(2, RoundingMode.HALF_UP)));
        return items;
    }


    public Item getItem(String sku) {
        return new Item(items.get(sku));
    }

    public StaticLoadItemsServiceImpl getStaticLoadItemsService() {
        return staticLoadItemsService;
    }

}
