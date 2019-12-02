package au.com.dius.shop.cart.engine;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.dius.shop.cart.Item;
import au.com.dius.shop.cart.rules.Expression;
import au.com.dius.shop.cart.rules.Rules;

/**
 * Rules Engine is responsible to load the rules and
 * process the rules on a checkout function or any other
 * activities that are required to process the rules
 */
public class RulesEngine {

    private static List<Expression> expressions = new ArrayList<>();
    private static RulesEngine engine;
    private static String regex = "(^atv|ipd|mbp|vga) size (=|>|<) ([0-9]), (discount|free|newprice) (atv|ipd|mbp|vga|\\d+(\\.\\d{1,2})?)";
    private RulesEngine() {
    }

    public static RulesEngine getEngine() {
        if (engine == null) {
            engine = new RulesEngine();
            loadEngineConfigs();
        }
        return engine;
    }

    /**
     * To load the engine configs
     */

    private static void loadEngineConfigs() {
        try {
            InputStream inputStream = RulesEngine.class.getClassLoader().getResourceAsStream("rules.txt");
            if (inputStream != null) {
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);
                for (String line; (line = reader.readLine()) != null; ) {
                    if( validateExpressions(line)) {
                        expressions.add(new Expression(line));
                    } else {
                        //Raise a exception
                    }
                }
            }
        } catch (IOException e) {
            //Log messages need to come
            e.printStackTrace();
        }
    }

    //This regex has to move out of the the method
    public static boolean validateExpressions(String exp) {
        //(^atv|ipd|mbp|vga) size (=|>|<) ([0-9]), (discount|free|newprice) (atv|ipd|mbp|vga|\d+(\.\d{1,2})?)
        Pattern p = Pattern.compile(regex);//. represents single character
        Matcher m = p.matcher(exp);
        return m.matches();
    }

    /**
     * To process the engine rules
     *
     * @param engineContext
     * @param scannedItem
     */
    public void processEngine(EngineContext engineContext, Item scannedItem) {
        Rules rules = new Rules();
        rules.eval(scannedItem, engineContext.getCart().getAllItems(), expressions, engineContext.getCart());
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
