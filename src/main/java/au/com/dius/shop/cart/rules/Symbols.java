package au.com.dius.shop.cart.rules;

public enum Symbols {
    _eq ("="), _graterthan(">"), _for ("for"), _if ("if"), _lessthan ("<"),
    _newprice ("newprice"), _discount ("discount"), _size ("size"), _free("free");

    private String symbol;

    public String getSymbol() { return symbol; }
    Symbols(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
