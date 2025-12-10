package com.signup.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyAppUserService implements UserDetailsService {

    private final MyAppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyAppUser user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // 👇 build roles/authorities
        List<GrantedAuthority> authorities = new ArrayList<>();

        // every user has at least ROLE_USER
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // if is_admin = true in DB → also add ROLE_ADMIN
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
