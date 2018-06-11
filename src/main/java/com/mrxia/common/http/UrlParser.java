package com.mrxia.common.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.springframework.data.util.Pair;

import com.google.common.base.Splitter;

/**
 * URL 解析工具
 * @author xiazijian
 */
public class UrlParser {

    private URL url;

    private UrlParser(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    /** 获得url解析对象
     * @param url url字符串
     * @return 通过url字符串生成的解析对象
     */
    public static UrlParser of(String url) {
        try {
            return new UrlParser(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("url 参数不合法");
        }
    }

    /**
     * 获取url的请求参数map
     * @return key -> value 对应的请求参数Map
     */
    public Map<String, String> queryMap() {
        String queryString = this.url.getQuery();
        return StreamSupport
                .stream(Splitter.on("&").split(queryString).spliterator(), true)
                .map(kv -> {
                    String[] keyValue = kv.split("=");
                    return Pair.of(keyValue[0], keyValue[1]);
                })
                .collect(Pair.toMap());
    }

}
