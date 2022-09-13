package com.example.trellox.service;

import com.example.trellox.dto.LoginDto;
import com.example.trellox.dto.TokenDto;
import com.example.trellox.model.Customer;

public interface CustomerService {
    Customer findCustomerByEmail(String email);

    TokenDto register(LoginDto loginDto);

    TokenDto login(LoginDto loginDto);
}
