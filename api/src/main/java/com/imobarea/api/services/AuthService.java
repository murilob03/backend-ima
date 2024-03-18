package com.imobarea.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.imobarea.api.repositories.ClienteRepositorio;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    ClienteRepositorio clienteRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clienteRepo.findByTelefone(username);
    }
    
}
