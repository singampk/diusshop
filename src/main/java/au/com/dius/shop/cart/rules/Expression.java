package au.com.dius.shop.cart.rules;

/**
 * Expression keeps the rule and action
 */
public class Expression {

    private String expression;
    private String action;

    public Expression(String exp) {
        String[] expAndAction = exp.split(",");
        setExpression(expAndAction[0]);
        setAction(expAndAction[1]);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String toString() {
        return expression + "," + action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
