package com.signup.demo.security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class ActiveUserService {
    // store distinct usernames currently logged in
    private final Set<String> activeUsers = ConcurrentHashMap.newKeySet();

    public void userLoggedIn(String username) {
        if (username != null) activeUsers.add(username);
    }

    public void userLoggedOut(String username) {
        if (username != null) activeUsers.remove(username);
    }

    public int getActiveCount() {
        return activeUsers.size();
    }

    public Set<String> getActiveUsers() {
        return Set.copyOf(activeUsers);
    }
}
