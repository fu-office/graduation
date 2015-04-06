package com.lbyt.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * 类名: HttpContextHolder.
 * 描述: 利用ThreadLocal类,暂存Http上下文.为单例模式.
 * 
 * @author murphy
 */
public final class HttpContextHolder {
    /**
     * 变量名: multipartFileMap.
     * 描述: 暂存的multi part file.
     * 取值含义: 客户端上传的文件map.
     */
    private final ThreadLocal<MultiValueMap<String, MultipartFile>> multipartFileMap = new ThreadLocal<>();

    /**
     * 变量名: response.
     * 描述: 暂存的response对象.
     * 取值含义: 当前请求上下文的response对象.
     */
    private final ThreadLocal<HttpServletResponse>                  response         = new ThreadLocal<>();

    /**
     * 变量名: request.
     * 描述: 暂存的request对象.
     * 取值含义: 当前请求上下文的request对象.
     */
    private final ThreadLocal<HttpServletRequest>                   request          = new ThreadLocal<>();

    /**
     * 变量名: holder.
     * 描述: 单例对象.
     * 取值含义: HttpContextHolder的单例对象.
     */
    private static HttpContextHolder                                holder           = new HttpContextHolder();

    /**
     * 为实现单例,HttpContextHolder私有构造函数.
     */
    private HttpContextHolder() {}

    /**
     * 描述: 设置multipartFileMap的值.
     * 
     * @param multipartFileMap
     *            当前线程所使用的文件Map
     */
    public static void setMultipartFileMap(final MultiValueMap<String, MultipartFile> multipartFileMap) {
        holder.multipartFileMap.set(multipartFileMap);
    }

    /**
     * 描述: 获得当前线程的multipartFileMap.
     * 
     * @return 当前线程的multipartFileMap
     */
    public static MultiValueMap<String, MultipartFile> getMultipartFileMap() {
        return holder.multipartFileMap.get();
    }

    /**
     * 描述: 设置当前response.
     * 
     * @param response
     *            当前请求的response对象.
     */
    public static void setResponse(final HttpServletResponse response) {
        holder.response.set(response);
    }

    /**
     * 描述: 获得当前请求的response对象.
     * 
     * @return 当前请求的response对象.
     */
    public static HttpServletResponse getResponse() {
        return holder.response.get();
    }

    /**
     * 描述: 设置当前请求的request对象.
     * 
     * @param request
     *            当前请求的request对象.
     */
    public static void setRequest(final HttpServletRequest request) {
        holder.request.set(request);
    }

    /**
     * 描述: 获得当前请求的request对象.
     * 
     * @return 当前请求的request对象.
     */
    public static HttpServletRequest getRequest() {
        return holder.request.get();
    }

    /**
     * 描述: 清空当前线程所有缓存住的状态.
     * 因为tomcat会重复使用之前的线程(而不是新建一个),如果不清除缓存,之前缓存的内容会影响后面的.
     */
    public static void clear() {
        holder.request.remove();
        holder.response.remove();
        holder.multipartFileMap.remove();
    }
}
