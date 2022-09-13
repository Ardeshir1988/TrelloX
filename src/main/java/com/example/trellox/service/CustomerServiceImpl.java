package com.example.trellox.service;

import com.example.trellox.dto.LoginDto;
import com.example.trellox.dto.TokenDto;
import com.example.trellox.enums.Role;
import com.example.trellox.model.Customer;
import com.example.trellox.repository.CustomerRepository;
import com.example.trellox.security.JwtUtils;
import com.example.trellox.security.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final JwtUtils jwtUtils;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(JwtUtils jwtUtils, CustomerRepository customerRepository) {
        this.jwtUtils = jwtUtils;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("customer not found"));
    }

    @Override
    public TokenDto register(LoginDto loginDto) {
        Optional<Customer> email = customerRepository.findByEmail(loginDto.getEmail());
        if (email.isPresent())
            throw new RuntimeException("This email is already registered");

        Customer customer = customerRepository.save(Customer.builder()
                .email(loginDto.getEmail())
                .password(loginDto.getPassword())
                .roles(List.of(Role.CUSTOMER))
                .build());

        String token = jwtUtils.generateToken(customer);
        return TokenDto.builder().token(token).message("customer registered").build();
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        Customer customer = customerRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new RuntimeException("customer not found"));
        if (!PasswordUtils.doPasswordsMatch(loginDto.getPassword(),customer.getPassword()))
            throw new RuntimeException("email or password is wrong");
        return TokenDto.builder().token(jwtUtils.generateToken(customer)).build();
    }
}
