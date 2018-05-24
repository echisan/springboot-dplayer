package cn.echisan.springbootdplayer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneralUtils {

    private static final Logger logger = LoggerFactory.getLogger(GeneralUtils.class);

    /**
     * 获取请求的ip地址
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        logger.info("请求的ip地址: {}", ip);
        return ip;
    }

    /**
     * 获取请求域名
     *
     * @param request 请求
     * @return 域名
     */
    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    /**
     * 获取32位无分隔号小写UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 过滤html标签 清除xxs
     * @param html 弹幕内容
     * @return 过滤后的内容
     */
    public static String htmlEncode(String html) {
        if (StringUtils.hasText(html)) {
            html = html.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                    .replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;")
                    .replaceAll("'", "&#39;")
                    .replaceAll("eval\\((.*)\\)", "")
                    .replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"")
                    .replaceAll("script", "");
            return html;
        }
        return "";
    }

    /**
     * 加载黑名单列表
     * @return 黑名单列表
     */
    public static List<String> loadBlackList(){
        List<String> blackList = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"blacklist");
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String str = "";
            while ((str = br.readLine())!=null){
                blackList.add(str);
            }
            return blackList;
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("黑名单文件不存在");
            return blackList;
        }
    }

    public static List<String> loadBlackListInJar(Class controllerClazz) throws IOException {
        List<String> blackList = new ArrayList<>();
        InputStream is = controllerClazz.getResourceAsStream(ResourceUtils.CLASSPATH_URL_PREFIX+"blacklist");
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        String str = "";
        while ((str = br.readLine())!=null){
            blackList.add(str);
        }
        return blackList;
    }
}
