package exchange.calcul.util;

import java.text.DecimalFormat;

public class CurrencyUtil {

    public static String roundTwo(Double price){
        DecimalFormat form = new DecimalFormat("#,##0.00");
        return form.format(price);
    }
}
