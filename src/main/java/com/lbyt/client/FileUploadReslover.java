package com.lbyt.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 类名: FileUploadResolver.
 * 描述: 处理文件上传功能的Resolver,在进入Controller方法之前,将request中的Multi File Map取出放入当前线程.
 * 
 * @author murphy
 */
public class FileUploadReslover extends CommonsMultipartResolver {
    /* (non-Javadoc)
     * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#resolveMultipart(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
        MultipartHttpServletRequest result = super.resolveMultipart(request);
        HttpContextHolder.setMultipartFileMap(result.getMultiFileMap());
        return result;
    }
}
