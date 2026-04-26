package com.aj.travel.auth.security;

import com.aj.travel.user.domain.User;
import com.aj.travel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticationService.class);
    private static final String ROLE_PREFIX = "ROLE_";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.debug("Loading user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        if (user.getRole() == null) {
            throw new IllegalStateException("User has no role assigned");
        }

        var authorities = List.of(
                new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name())
        );

        return new AuthenticatedUserPrincipal(
                user.getId(),
                user.getEmail(),
                authorities
        ).withPassword(user.getPassword());
    }
}