package com.signup.demo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ActiveUserService activeUserService;

    public CustomAuthenticationSuccessHandler(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        activeUserService.userLoggedIn(username);

        // attach username to session for easier logout tracking
        HttpSession session = request.getSession(true);
        session.setAttribute("username", username);

        // redirect to default (Spring Security default handling or your configured default)
        response.sendRedirect(request.getContextPath() + "/index");
    }
}
