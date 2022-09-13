package com.example.trellox.service;

import com.example.trellox.dto.LoginDto;
import com.example.trellox.dto.TokenDto;
import com.example.trellox.model.Customer;
import com.example.trellox.repository.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return null;
    }

    @Override
    public TokenDto register(LoginDto loginDto) {
        return null;
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        return null;
    }
}
