package www.ccb.com.common.utils;

public class NumberFormatUtil {
  
    static String[] units = {"","十","百","千","万","十万","百万","千万","亿","十亿","百亿","千亿","万亿" };  
    static char[] numArray = {'零','一','二','三','四','五','六','七','八','九'};  

    /**
     * 将整数转换成汉字数字
     * @param num 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatInteger(int num) {  
        char[] val = String.valueOf(num).toCharArray();  
        int len = val.length;  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < len; i++) {  
            String m = val[i] + "";  
            int n = Integer.valueOf(m);  
            boolean isZero = n == 0;  
            String unit = units[(len - 1) - i];  
            if (isZero) {  
                if ('0' == val[i - 1]) {  
                    continue;  
                } else {  
                    sb.append(numArray[n]);  
                }  
            } else {  
                sb.append(numArray[n]);  
                sb.append(unit);  
            }  
        }  
        return sb.toString();  
    }  
    /**
     * 将小数转换成汉字数字
     * @param decimal 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatDecimal(double decimal) {  
        String decimals = String.valueOf(decimal);  
        int decIndex = decimals.indexOf(".");  
        int integ = Integer.valueOf(decimals.substring(0, decIndex));  
        int dec = Integer.valueOf(decimals.substring(decIndex + 1));  
        String result = formatInteger(integ) + "." + formatFractionalPart(dec);  
        return result;  
    }

    /**
     * 格式化小数部分的数字
     * @param decimal 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatFractionalPart(int decimal) {
        char[] val = String.valueOf(decimal).toCharArray();  
        int len = val.length;  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < len; i++) {  
            int n = Integer.valueOf(val[i] + "");  
            sb.append(numArray[n]);  
        }  
        return sb.toString();  
    }  
} 