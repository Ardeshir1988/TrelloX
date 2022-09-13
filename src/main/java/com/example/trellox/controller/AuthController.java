package com.example.trellox.controller;

import com.example.trellox.dto.LoginDto;
import com.example.trellox.dto.TokenDto;
import com.example.trellox.service.CustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signUp")
    public TokenDto registerCustomer(@Validated @RequestBody LoginDto loginDto){
        return customerService.register(loginDto);
    }

    @PostMapping("/login")
    public TokenDto login(@Validated @RequestBody LoginDto loginDto){
        return customerService.login(loginDto);
    }
}
