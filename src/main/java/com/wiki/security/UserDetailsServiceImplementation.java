package com.wiki.security;

import com.wiki.model.Identity;
import com.wiki.repository.IdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImplementation implements org.springframework.security.core.userdetails.UserDetailsService {

    private IdentityRepository identityRepository;

    @Autowired
    public UserDetailsServiceImplementation(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Identity user = identityRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), getAuthority(user.getRoles()));
    }

    private Set<SimpleGrantedAuthority> getAuthority(Set<String> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role))

        );
        return authorities;
    }
}
