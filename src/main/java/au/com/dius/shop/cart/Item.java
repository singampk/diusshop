package au.com.dius.shop.cart;

import java.math.BigDecimal;


/**
 * Checkout item
 */
public class Item {
    private int id;
    private String name;
    private String sku;
    private final BigDecimal price;
    private BigDecimal discountedPrice = new BigDecimal(-1);
    private boolean discountLock = false;
    private boolean isDiscountedItem = false;
    private boolean isScanConfirmedRequired = false;

    public Item(String name, String sku, BigDecimal price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    public Item(Item item) {
        this.price = item.getPrice();
        this.sku = item.getSku();
        this.name = item.getName();
        this.discountedPrice = item.getDiscountedPrice();
        this.discountLock = item.isDiscountLock();
        this.isDiscountedItem = item.isDiscountedItem();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        if (getDiscountedPrice().compareTo(BigDecimal.ZERO) >= 0 )
            return discountedPrice;
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public boolean isDiscountLock() {
        return discountLock;
    }

    public void setDiscountLock(boolean discountLock) {
        this.discountLock = discountLock;
    }

    public boolean isDiscountedItem() {
        return isDiscountedItem;
    }

    public void setDiscountedItem(boolean discountedItem) {
        isDiscountedItem = discountedItem;
    }

    public boolean isScanConfirmedRequired() {
        return isScanConfirmedRequired;
    }

    public void setScanConfirmedRequired(boolean scanConfirmedRequired) {
        isScanConfirmedRequired = scanConfirmedRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item simpson = (Item) o;
        return id == simpson.id &&
                name.equals(simpson.name);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
