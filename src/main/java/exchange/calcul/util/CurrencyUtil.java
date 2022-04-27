package exchange.calcul.util;

import java.text.DecimalFormat;
import java.text.ParseException;

public class CurrencyUtil {

    public static String roundTwo(Double price){
        DecimalFormat form = new DecimalFormat("#,##0.00");
        return form.format(price);
    }

    public static Double StringToNumber(String price){
        DecimalFormat form = new DecimalFormat();
        Number num = null;
        try {
            num = form.parse(price);
        } catch (ParseException e) {
            throw new IllegalStateException("입력받은 숫자 형태가 다릅니다.");
        }
        return  num.doubleValue();
    }
}
