package com.moonciki.interview.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;
import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * @author ysq
 */
@Slf4j
public class CommonUtil {

    static String timeStr;
    static volatile int tsn = 0;

    /**
     * 生成随机数，包含起始
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRundomNumber(int min, int max) {
        if(max < min){
            throw CustomException.createException(ResponseEnum.request_error.info());
        }

        Random random = new Random();

        int result = random.nextInt(max - min + 1) + min;

        return result;
    }

    /**
     * 生成唯一 id
     * 支持毫秒万以内
     * @return
     */
    public static String getUniqueKey() {

        String dayType = "yyyyMMddHHmmssSSSS";
        String timeStamp = DateUtil.getNowTime(dayType);

        if (!timeStamp.equals(timeStr)) {
            timeStr = timeStamp;
            tsn = 0;
        } else {
            tsn++;
        }

        String tsnNumber = String.format("%04d", tsn);

        int ran = getRundomNumber(0, 999);
        String ranNumber = String.format("%03d", ran);

        String reuslt = timeStamp + tsnNumber + ranNumber;

        return reuslt;
    }

    /**
     * 获得 文本文件内容
     *
     * @param path
     * @return
     */
    public static String getFileContent(String path) {
        StringBuilder builder = new StringBuilder();

        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(path);
            reader = new BufferedReader(fr);
            String line = null;

            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                if(lineCount > 0){
                    builder.append("\r\n");
                }
                builder.append(line);

                lineCount++;
            }

            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fr != null){
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    /**
     * 将文本写入文件
     *
     * @param path
     * @param content
     * @param append
     */
    public static void writeFileContent(String path, String content, boolean append) {

        if(content == null){
            return;
        }

        createFileFolder(path);

        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(path, append);
            writer = new BufferedWriter(fw);

            writer.write(content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 将二进制转换成base64字符串格式
     *
     * @param byteData
     * @return
     */
    public static String encodeBase64(byte[] byteData) {
        String result = Base64.encodeBase64String(byteData);
        return result;
    }

    /**
     * base64 格式
     *
     * @param baseText
     * @return
     */
    public static byte[] decodeBase64(String baseText) {
        byte[] data = Base64.decodeBase64(baseText);
        return data;
    }

    /**
     * 将数据写入文件
     *
     * @param path
     * @param dataByte
     * @param append
     */
    public static void writeFile(String path, byte[] dataByte, boolean append) {

        createFileFolder(path);

        OutputStream output = null;
        try {
            output = new FileOutputStream(path, append);

            output.write(dataByte);
        } catch (Exception e) {
            throw CustomException.createException(e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    throw CustomException.createException(e);
                }
            }
        }
    }

    /**
     * byte 数组拼接追加
     *
     * @param byteArrayList
     * @return
     */
    public static byte[] joinAllBytes(byte[]... byteArrayList) throws Exception {
        byte[] resultBytes = null;

        ByteArrayOutputStream byteOut = null;

        byteOut = new ByteArrayOutputStream();
        try {
            for (byte[] oneArray : byteArrayList) {
                byteOut.write(oneArray);
            }

            resultBytes = byteOut.toByteArray();

            byteOut.reset();

        } finally {
            if (byteOut != null) {
                try {
                    byteOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultBytes;
    }

    /**
     * byte 数组拼接追加
     *
     * @param byteArrayList
     * @return
     */
    public static byte[] joinAllBytes(List<byte[]> byteArrayList) {

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        for (byte[] oneArray : byteArrayList) {
            try {
                byteOut.write(oneArray);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        byte[] resultBytes = byteOut.toByteArray();

        byteOut.reset();
        try {
            byteOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resultBytes;
    }

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static byte[] readFileData(String path) {
        byte[] resultByte = null;

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        InputStream input = null;
        try {
            input = new FileInputStream(path);

            int readLength = 0;

            byte[] tmpByte = new byte[4 * 1024];

            while ((readLength = input.read(tmpByte)) > 0) {

                byteOut.write(tmpByte, 0, readLength);

            }

            resultByte = byteOut.toByteArray();

        } catch (Exception e) {
            throw CustomException.createException(e);

        } finally {
            try {
                input.close();

                byteOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultByte;
    }

    /**
     * 创建文件包括目录
     *
     * @param pathFile
     * @return
     * @throws IOException
     */
    public static void createFile(File pathFile) throws IOException {
        File parentFile = pathFile.getParentFile();

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!pathFile.exists()) {
            pathFile.createNewFile();
        }
    }

    /**
     * 创建文件包括目录
     * @param filePath
     * @return
     * @throws IOException
     */
    public static void createFileFolder(String filePath) {

        File parentFile = new File(filePath).getParentFile();

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    /**
     * 输出 Ajax 返回的内容
     */
    public static void printAjaxResult(ServletResponse response, String result) {
        log.debug("pringResult=>" + result);

        //设置输出UTF-8编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        //获得输出流
        PrintWriter out = null;

        try {
            out = response.getWriter();
            //将数据写入输出流
            out.write(result);
            //清楚数据流缓冲区
            out.flush();
        } catch (IOException e) {
            log.error("Response Error", e);
        } finally {
            if (out != null) {
                //关闭输出流
                out.close();
            }
        }
    }

    /**
     * 创建文件包括目录
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File createFile(String path) throws IOException {
        File resultFile = new File(path);

        createFile(resultFile);

        return resultFile;
    }

    /**
     * 是否都为空
     * @param objArray
     * @return
     */
    public static boolean isAllNull(Object ... objArray){
        boolean result = true;

        for(Object obj : objArray){

            if(obj != null){
                result = false;

                break;
            }

        }
        return result;
    }

    /**
     * 是否包含空
     * @param objArray
     * @return
     */
    public static boolean hasNull(Object ... objArray){
        boolean result = false;

        for(Object obj : objArray){

            if(obj == null){
                result = true;

                break;
            }

        }
        return result;
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        //先 md5
        String onece = DigestUtils.md5Hex(password).toUpperCase();
        //再 sha1
        String two = DigestUtils.sha1Hex(onece).toUpperCase();
        //再 sha1
        String three = DigestUtils.sha1Hex(two).toUpperCase();

        return three;
    }

    /**
     * 类字段赋值验证
     *
     * @param clazz
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static boolean fastJsonValidate(Class clazz, String fieldName, Object fieldValue) {

        boolean flag = true;

        if (fieldValue != null) {

            try {
                Field field = clazz.getDeclaredField(fieldName);

                if (field != null) {

                    Class typeClazz = field.getType();

                    if (typeClazz != null) {
                        if (typeClazz.isAssignableFrom(Date.class)) {
                            //nothing to do

                        } else if (typeClazz.isAssignableFrom(Boolean.class)) {
                            TypeUtils.castToBoolean(fieldValue);

                        } else if (typeClazz.isAssignableFrom(String.class)) {
                            TypeUtils.castToString(fieldValue);

                        } else if (typeClazz.isAssignableFrom(Double.class)) {
                            TypeUtils.castToDouble(fieldValue);

                        } else if (typeClazz.isAssignableFrom(Float.class)) {
                            TypeUtils.castToFloat(fieldValue);

                        } else if (typeClazz.isAssignableFrom(Byte.class)) {
                            TypeUtils.castToByte(fieldValue);

                        } else if (typeClazz.isAssignableFrom(Short.class)) {
                            TypeUtils.castToShort(fieldValue);

                        } else if (typeClazz.isAssignableFrom(Integer.class)) {
                            TypeUtils.castToInt(fieldValue);

                        } else if (typeClazz.isAssignableFrom(Long.class)) {
                            TypeUtils.castToLong(fieldValue);

                        } else {
                            throw CustomException.createException(ResponseEnum.sys_error.info("字段类型错误"));
                        }
                    }

                }
            } catch (Exception e) {
                log.info("Validate Error : ", e);
                flag = false;
            }

        }
        return flag;
    }

    /**
     * 计算百分比
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return
     */
    public static String percentage(int numerator, int denominator) {
        String result = "100.00%";

        if (denominator == 0) {

        } else {
            DecimalFormat df = new DecimalFormat("0.00%");

            BigDecimal fenzi = new BigDecimal(numerator);
            BigDecimal fenmu = new BigDecimal(denominator);

            BigDecimal decResult = fenzi.divide(fenmu, 4, BigDecimal.ROUND_HALF_UP);

            result = df.format(decResult);

        }

        return result;
    }

    /**
     * 计算百分比
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return
     */
    public static int percentageInt(int numerator, int denominator) {
        int result = 100;

        if (denominator == 0) {

        } else {

            BigDecimal fenzi = new BigDecimal(numerator * 100);

            BigDecimal fenmu = new BigDecimal(denominator);

            BigDecimal decResult = fenzi.divide(fenmu, 0, BigDecimal.ROUND_DOWN);

            result = decResult.intValue();
            if (result > 1) {
                result--;
            }
        }

        return result;
    }

    /**
     * 正则校验
     *
     * @param reg
     * @param text
     * @return
     */
    public static boolean regMatch(String reg, String text) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(text);

        boolean result = matcher.matches();

        return result;
    }

    /**
     * 用冒号(:)拼接 redis key
     *
     * @param strArray
     * @return
     */
    public static String joinRedisKey(String... strArray) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        boolean endwithSplit = false;
        for (String onePart : strArray) {

            if (i > 0 && !endwithSplit) {
                builder.append(":");
            }

            builder.append(onePart);

            if (onePart.endsWith(":")) {
                endwithSplit = true;
            } else {
                endwithSplit = false;
            }

            i++;
        }

        return builder.toString();
    }

    /**
     * 生成uuid
     *
     * @param nonSplit 是否过滤分隔符
     * @return
     */
    public static String getUUID(boolean nonSplit) {
        String uuid = UUID.randomUUID().toString().toUpperCase();

        if (nonSplit) {
            uuid = uuid.replaceAll("-", "");
        }

        return uuid;
    }

    /**
     * 获得文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        String suffix = "";

        if (StringUtils.isBlank(fileName)) {
            return suffix;
        }

        File tmpFile = new File(fileName);

        String nameOnly = tmpFile.getName();

        int index = nameOnly.lastIndexOf(".");

        if (index >= 0) {
            suffix = nameOnly.substring(index).toLowerCase();
        }
        return suffix;
    }

    /**
     * 拼接文件路径
     * 统一用 Linx 路径分隔符
     *
     * @param partArray
     * @return
     */
    public static String joinPath(String... partArray) {
        String fullPath = null;

        if (partArray != null) {
            fullPath = "";

            for (String onePart : partArray) {
                if (StringUtils.isNotBlank(onePart)) {

                    onePart = onePart.replace('\\', '/');

                    if (fullPath.length() > 0) {

                        if(onePart.startsWith("/")){
                            onePart = onePart.replaceAll("^/+", "");
                        }
                    }
                    if(StringUtils.isNotBlank(onePart)){
                        if (fullPath.length() > 0) {
                            if (!fullPath.endsWith("/")) {
                                fullPath = fullPath + "/";
                            }
                        }
                        fullPath = fullPath + onePart;
                    }
                }
            }
        }

        return fullPath;
    }

    /**
     * 将对象转成map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> result = null;

        if (obj != null) {
            String json = JSON.toJSONString(obj);

            result = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
            });
        }

        return result;
    }

    /**
     * 删除文件及下级所有文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        //判断是否待删除目录是否存在
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            //取得当前目录下所有文件和文件夹
            File[] content = file.listFiles();

            for (File temp : content) {
                //递归调用，删除目录里的内容
                deleteFile(temp.getAbsolutePath());
            }
        }

        file.delete();

        return true;
    }

    /**
     * BCrypt 加密
     * @param password
     * @return
     */
    public static String encoderBCrypt(String password){
        String encResult = BCrypt.hashpw(password, BCrypt.gensalt());

        return encResult;
    }

    /**
     * BCrypt 校验
     * @param origiText 原始内容
     * @param encText 加密后的内容
     * @return
     */
    public static boolean matcheBCrypt(String origiText, String encText){

        boolean result = BCrypt.checkpw(origiText, encText);

        return result;
    }

    /**
     * 校验正则表达式
     * @param regex
     * @param content
     * @return
     */
    public static boolean regCheck(String regex, String content){
        boolean isMatch = Pattern.matches(regex, content);
        return isMatch;
    }

    public static boolean isValidIp(String ip) {
        String partReg = "((25[0-5])|(2[0-4]\\d)|(1\\d{2})|([1-9]\\d)|([0-9]))";

        String ipReg = "^" + partReg + "\\." + partReg + "\\." + partReg + "\\." + partReg + "$";

        boolean match = regMatch(ipReg, ip);

        return match;
    }

    /**
     * 获取远端Ip地址
     * @param request
     * @return
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String unknown = "unknown";
        String ipStr = "";
        try {
            ipStr = request.getHeader("X-Forwarded-For");

            if (StringUtils.isBlank(ipStr) || unknown.equalsIgnoreCase(ipStr)) {
                ipStr = request.getHeader("X-Real-IP");
            }
            if (StringUtils.isBlank(ipStr) || unknown.equalsIgnoreCase(ipStr)) {
                ipStr = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipStr) || unknown.equalsIgnoreCase(ipStr)) {
                ipStr = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipStr) || unknown.equalsIgnoreCase(ipStr)) {
                ipStr = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isBlank(ipStr) || unknown.equalsIgnoreCase(ipStr)) {
                ipStr = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isBlank(ipStr) || unknown.equalsIgnoreCase(ipStr)) {
                ipStr = request.getRemoteAddr();
            }

            if(StringUtils.isNotEmpty(ipStr) && !unknown.equalsIgnoreCase(ipStr)){
                //多次反向代理后会有多个ip值，第一个ip才是真实ip
                //为防止暴露服务器ip
                /*
                if(ipStr.equals("127.0.0.1") || ipStr.equalsIgnoreCase("localhost")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();

                        ipStr = inet.getHostAddress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                */
                if(ipStr.equals("0.0.0.0")) {
                    ipStr = "127.0.0.1";
                }

                int index = ipStr.indexOf(",");
                if(index != -1) {
                    ipStr = ipStr.substring(0, index);
                }
            }

            if(!isValidIp(ipStr)){
                ipStr = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ipStr;
    }

    /**
     * 获取远端Ip地址
     * @return
     */
    public static String getIpAdrress(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        return getIpAdrress(request);
    }

    /**
     * 判断是否相同
     * @param o1
     * @param o2
     * @param allowNull 为空时，是否一样
     * @return
     */
    public static boolean objEquels(Object o1, Object o2, boolean allowNull) {
        boolean result = false;
        if (allowNull) {
            if (o1 == null && o2 == null) {
                return true;
            }
        }

        if (o1 != null && o2 != null) {
            result = o1.equals(o2);
        }

        return result;
    }

    /**
     * 是否全部为真
     * @param flags
     * @return
     */
    public static boolean allTrue(Boolean ... flags){

        boolean booleanResult = true;

        if(flags != null) {
            for (Boolean oneBolean : flags) {
                if(oneBolean == null || !oneBolean){
                    booleanResult = false;
                    break;
                }
            }
        }

        return booleanResult;
    }

    public static void reportSpend(long startTime){

        long endTime = System.currentTimeMillis();

        long spend = endTime - startTime;

        long spendSec = spend / 1000L;

        log.info("Spend [{}] s", spendSec);
    }

    public static void main(String[] args) {

        String s = encryptPassword("123456@#");

        String s1 = encoderBCrypt(s);

        System.out.println(s1);

        System.out.println("finished ... ");
    }

}
