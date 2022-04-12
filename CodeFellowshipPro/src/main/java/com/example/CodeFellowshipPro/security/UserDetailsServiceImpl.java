package com.example.CodeFellowshipPro.security;

import com.example.CodeFellowshipPro.models.ApplicationUser;
import com.example.CodeFellowshipPro.repositories.ApplicationUserRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        ApplicationUser user=applicationUserRepository.findUserByUsername(username);
        System.out.println(user);
        return user;
    }
}
