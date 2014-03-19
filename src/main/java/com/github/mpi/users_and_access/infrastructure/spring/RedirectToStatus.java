package com.github.mpi.users_and_access.infrastructure.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RedirectToStatus implements AuthenticationSuccessHandler, AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        
        response.sendRedirect(computeRedirectionUrl(request));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        response.sendRedirect(computeRedirectionUrl(request) + "?authToken=" + request.getSession().getId());
    }

    private String computeRedirectionUrl(HttpServletRequest request) {

        String redirectTo = "/endpoints/v1/authentication/status";

        if(request.getParameter("redirect_to") != null){
            redirectTo = request.getParameter("redirect_to");
        }
        return redirectTo;
    }

}
