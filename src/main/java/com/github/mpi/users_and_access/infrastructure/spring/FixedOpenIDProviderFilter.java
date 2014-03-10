package com.github.mpi.users_and_access.infrastructure.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.security.openid.OpenIDAuthenticationFilter;

public class FixedOpenIDProviderFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        chain.doFilter(new FixedOpenIDProviderRequest(request), response);
    }

    private final class FixedOpenIDProviderRequest extends HttpServletRequestWrapper {

        public FixedOpenIDProviderRequest(ServletRequest request) {
            super((HttpServletRequest) request);
        }

        @Override
        public String getParameter(String name) {
            
            if(OpenIDAuthenticationFilter.DEFAULT_CLAIMED_IDENTITY_FIELD.equals(name)){
                return "https://www.google.com/accounts/o8/id?openid.ui.icon=true";
            }
            
            return super.getParameter(name);
        }
    }
}
