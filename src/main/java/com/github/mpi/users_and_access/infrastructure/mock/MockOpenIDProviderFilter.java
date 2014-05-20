package com.github.mpi.users_and_access.infrastructure.mock;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationFilter;

import com.google.common.collect.Sets;

public class MockOpenIDProviderFilter implements Filter{
    
    @Autowired
    private OpenIDAuthenticationFilter configuredFilter;
    
    @PostConstruct
    void configure(){
        configuredFilter.setReturnToUrlParameters(Sets.newHashSet("redirect_to"));
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        chain.doFilter(new MockOpenIDProviderRequest(request), response);
    }

    private String serverUrl(HttpServletRequest request) {
        return request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
    }
    
    private final class MockOpenIDProviderRequest extends HttpServletRequestWrapper {


        public MockOpenIDProviderRequest(ServletRequest request) {
            super((HttpServletRequest) request);
        }

        @Override
        public String getParameter(String name) {
            
            if(OpenIDAuthenticationFilter.DEFAULT_CLAIMED_IDENTITY_FIELD.equals(name)){
                return serverUrl((HttpServletRequest) super.getRequest()) + "/MockOpenID/discover";
            }
            
            return super.getParameter(name);
        }
    }
    
}
