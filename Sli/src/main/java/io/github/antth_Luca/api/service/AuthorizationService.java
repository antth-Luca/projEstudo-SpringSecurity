package io.github.antth_Luca.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.antth_Luca.api.repository.ClienteRepository;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    ClienteRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return userRepo.findByLogin(cpf);
    }

}
