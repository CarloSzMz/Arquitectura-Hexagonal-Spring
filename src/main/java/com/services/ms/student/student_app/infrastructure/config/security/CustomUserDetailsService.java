package com.services.ms.student.student_app.infrastructure.config.security;

import com.services.ms.student.student_app.application.ports.input.UserServicePort;
import com.services.ms.student.student_app.domain.exception.UserNotFoundException;
import com.services.ms.student.student_app.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServicePort userServicePort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userServicePort.findByUsername(username);
            return new CustomUserPrincipal(user);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

    public static class CustomUserPrincipal implements UserDetails {
        private final User user;

        public CustomUserPrincipal(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if (user.getRoles() == null) {
                return java.util.Collections.emptyList();
            }
            
            return user.getRoles().stream()
                    .flatMap(role -> {
                        SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
                        java.util.List<GrantedAuthority> permissionAuthorities = new java.util.ArrayList<>();
                        
                        if (role.getPermissions() != null) {
                            role.getPermissions().stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                                .forEach(permissionAuthorities::add);
                        }
                        
                        permissionAuthorities.add(roleAuthority);
                        return permissionAuthorities.stream();
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return user.isEnabled();
        }

        public User getUser() {
            return user;
        }
    }
}