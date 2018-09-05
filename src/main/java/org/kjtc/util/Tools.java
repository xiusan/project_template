package org.kjtc.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kjtc.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class Tools {

    private static Logger logger = Logger.getLogger(Tools.class);

    public static String int2HexStr (int value) {
        String hexStr = Integer.toHexString(value & 0xFF);
        if (hexStr.length() == 1) {
            hexStr = "0" + hexStr;
        }
        return hexStr.toUpperCase();
    }

    public static String bytes2HexStr (byte[] bytes) {
        int length = bytes.length;
        String hexStr = "";
        for (int i = 0; i < length; i++) {
            hexStr += int2HexStr(bytes[i]).toUpperCase();
        }
        return hexStr;
    }

    public static int hex2Int(byte[] message, int begin, int length) {
        int messageLength = message.length;
        String strHex = "";
        for (int i = 0; i < length && (begin + i) < messageLength; i++) {
            strHex += int2HexStr(message[begin + i]);
        }
        if (strHex == null || strHex.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(strHex, 16);
        }
    }

    public static String byte2Str(byte[] message, int begin, int length, String charsetName) {
        int messageLength = message.length;
        byte[] byteHex = new byte[length];
        int k = 0;
        for (int i = 0; i < length && (begin + i) < messageLength; i++) {
            if (message[begin + i] != 0) {
                byteHex[i] = message[begin + i];
                k++;
            }
        }
        if (k == 0) {
            return "";
        } else if (k < length) {
            byteHex = new byte[k];
            for (int i = 0; i < length && (begin + i) < messageLength; i++) {
                if (message[begin + i] != 0) {
                    byteHex[i] = message[begin + i];
                }
            }
        }
        try {
            if (charsetName == null || charsetName.isEmpty()) {
                return new String(byteHex);
            } else {
                return new String(byteHex, charsetName);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("报文解析为中文时异常：" + e.getMessage());
            e.printStackTrace();
            return "parseError";
        }
    }

    public static byte[] byte2Bit(byte[] message, int begin, int length) {
        int messageLength = message.length;
        byte[] array = new byte[8 * length];
        byte m;
        for (int i = 0; i < length && (begin + i) < messageLength; i++) {
            m = message[i];
            for (int j = 0; j < 8; j++) {
                array[8 * (length - 1 - i) + j] = (byte)(m & 0x1);
                m = (byte)(m >> 1);
            }
        }
        return array;
    }

    public static String byte2BitStr(byte[] message, int begin, int length) {
        int messageLength = message.length;
        String bitStr = "";
        byte m;
        for (int i = length - 1; i >= 0 && (begin + i) < messageLength; i--) {
            m = message[i];
            for (int j = 7; j >= 0; j--) {
                bitStr += (byte)(m & 0x1);
                m = (byte)(m >> 1);
            }
        }
        return bitStr;
    }

    public static String hex2DateTime(byte[] message, int begin, int length) {
        String dateTime = "";
        int messageLength = message.length;
        for (int i = 0; i < length && (begin + i) < messageLength; i++) {
            if (i == 0) {
                dateTime += "20";
            } else if (i == 1) {
                dateTime += "-";
            } else if (i == 2) {
                dateTime += "-";
            } else if (i == 3) {
                dateTime += " ";
            } else if (i == 4) {
                dateTime += ":";
            } else if (i == 5) {
                dateTime += ":";
            }
            if (message[begin + i] < 10) {
                dateTime += "0";
            }
            dateTime += message[begin + i];
        }
        return dateTime;
    }
    public static int checkBBC(byte[] message, int begin, int last) {
        int messageLength = message.length;
        int checkValue = message[begin];
        for (int i = begin + 1; i <= last && (begin + i) < messageLength; i++) {
                checkValue = checkValue ^ message[i];
            }
        return checkValue;
    }
        /**
         * 提供精确的加法运算。
         *
         * @param value1 被加数
         * @param value2 加数
         * @return 两个参数的和
         */
    public static String add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).toString();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static String sub(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).toString();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static String mul(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).toString();
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String divide(String dividend, String divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(dividend);
        BigDecimal b2 = new BigDecimal(divisor);
        return b1.divide(b2, scale, RoundingMode.HALF_UP).toString();
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String value,int scale){
        if(scale < 0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(value);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).toString();
    }
    public static String byte2HexStr(byte[] b) {
        String strHex = null;
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < b.length; i++) {
            strHex = int2HexStr(b[i]);
            if (strHex.length() == 1) {
                strHex = "0" + strHex;
            }
            stringBuilder.append(strHex);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().toUpperCase().trim();
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date){
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照参数format的格式，日期转字符串
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format){
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }else{
            return "";
        }
    }

    /**
     * 按照yyyy-MM-dd的格式，字符串转datetime
     * @param date
     * @return
     */
    public static Date str2Date(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转datetime
     * @param date
     * @return
     */
    public static Date str2DateTime(String date){
        if (date.length() == 10) {
            date = date + " 00:00:00";
        } else if(date.length() == 13) {
            date = date + ":00:00";
        } else if (date.length() == 16) {
            date = date + ":00";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * gzip格式解压
     * @param inFileName 要解压的文件名
     */
    public static String unGzip(String inFileName) {
        String outFileName = null;
        try {
            File file = new File(inFileName);
            if (file.exists()) {
                GZIPInputStream in = new GZIPInputStream(new FileInputStream(inFileName));
                outFileName = inFileName.substring(0, inFileName.lastIndexOf('.'));
                FileOutputStream out = new FileOutputStream(outFileName);
                byte[] buf = new byte[4 * 1024];
                int len = 0;
                while((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.close();
                out = null;
                in.close();
                in = null;
                logger.info("GZIP文件解压成功:" + inFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("GZIP文件解压异常：" + e.getMessage());
        }
        return outFileName;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }
    public static boolean isNumericORLetter(String str){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }
    public static boolean isLetter(String str){
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }

    public static String encodePassword(String algorithm, String password, Object salt) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        BASE64Encoder base64en = new BASE64Encoder();
        if (password == null) {
            password = "";
        }
        if (!(salt == null) || "".equals(salt)) {
            password = password + "{" + salt.toString() + "}";
        }
        return base64en.encode(md5.digest(password.getBytes("UTF-8")));
    }

    /**
     * 得到request对象
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        return request;
    }

    /**
     * 得到response对象
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    public static Object getSessionAttribute(String attribute){
        return getRequest().getSession().getAttribute(attribute);
    }

    public static void setSessionAttribute(String attribute, Object obj){
        getRequest().getSession().setAttribute(attribute, obj);
    }

    public static String getUser(RedisTemplate<String, Object> redisTemplate){
        String user = "";
        if (getSessionAttribute("UUID") != null) {
            User userRedis = (User) redisTemplate.opsForValue().get((String) getSessionAttribute("UUID"));
            user = userRedis.getUserID();
        }
        return user;
    }
}
