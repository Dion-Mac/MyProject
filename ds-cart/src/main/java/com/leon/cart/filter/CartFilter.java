package com.leon.cart.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author LeonMac
 * @description
 */

public class CartFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("具体过滤规则");
    }

    @Override
    public void destroy() {
        System.out.println("销毁");
    }
}
