package com.signup.demo.security;

import org.springframework.stereotype.Component;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
@WebListener
public class SessionListener implements HttpSessionListener {

    private final ActiveUserService activeUserService;

    public SessionListener(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Object username = se.getSession().getAttribute("username");
        if(username instanceof String) {
            activeUserService.userLoggedOut((String) username);
        }
    }
}
