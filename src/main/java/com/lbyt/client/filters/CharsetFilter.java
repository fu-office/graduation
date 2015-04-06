package com.lbyt.client.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.lbyt.client.HttpContextHolder;

public class CharsetFilter extends OncePerRequestFilter{

	@Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        //设置默认字符集
        response.setCharacterEncoding("UTF-8");
        //缓存request对象
        HttpContextHolder.setRequest(request);
//        //缓存response对象
        HttpContextHolder.setResponse(response);
        //调用service
        filterChain.doFilter(request, response);
        //情况当前缓存的request和response
        HttpContextHolder.clear();
    }
	
}
