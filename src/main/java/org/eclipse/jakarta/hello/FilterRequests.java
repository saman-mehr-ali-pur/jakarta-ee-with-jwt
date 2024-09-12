package org.eclipse.jakarta.hello;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "authFilter",urlPatterns = {"/rest/*"},
        initParams = {@WebInitParam(name="mood",value = "easy")})
public class FilterRequests implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       //do nothing

        filterChain.doFilter(servletRequest,servletResponse);

    }
}
