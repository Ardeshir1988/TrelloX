package com.example.trellox.service;

import com.example.trellox.dto.LoginDto;
import com.example.trellox.dto.TokenDto;
import com.example.trellox.enums.Role;
import com.example.trellox.model.Customer;
import com.example.trellox.repository.CustomerRepository;
import com.example.trellox.security.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class, JwtUtils.class})
@TestPropertySource(locations = {"classpath:application.properties"})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private CustomerRepository customerRepository;

    private Customer getSampleCustomer() {
        return Customer.builder()
                .id("id-1")
                .email("a.gmail.com")
                .password("pass1234")
                .roles(List.of(Role.CUSTOMER))
                .build();
    }

    @Test
    public void findCustomerByEmail_returnCustomer() {
        when(customerRepository.findByEmail("a.gmail.com")).thenReturn(Optional.of(getSampleCustomer()));
        Customer customer = customerService.findCustomerByEmail("a.gmail.com");
        assertEquals(getSampleCustomer().getEmail(), customer.getEmail());
        assertEquals(getSampleCustomer().getRoles().size(), 1);
        assertEquals(getSampleCustomer().getRoles().stream().findAny().get(), Role.CUSTOMER);
    }

    @Test
    public void findCustomerByEmail_emailNotExisted_thenThrowRunException() {
        when(customerRepository.findByEmail("a.gmail.com")).thenReturn(Optional.empty());
        Throwable exception = assertThrows(RuntimeException.class, () -> customerService.findCustomerByEmail("a.gmail.com"));
        assertEquals("customer not found", exception.getMessage());
    }

    @Test
    public void registerCustomer_returnTokenDto() {
        when(customerRepository.save(Customer.builder().email(getSampleCustomer().getEmail()).password("pass1234").roles(List.of(Role.CUSTOMER)).build())).thenReturn(getSampleCustomer());
        TokenDto tokenDto = customerService.register(LoginDto.builder().email(getSampleCustomer().getEmail()).password("pass1234").build());
        assertEquals("customer registered", tokenDto.getMessage());
        assertEquals(Role.CUSTOMER.name(), jwtUtils.getRolesFromJwtToken(tokenDto.getToken()).get(0));
        assertEquals(getSampleCustomer().getEmail(), jwtUtils.getEmailFromToken(tokenDto.getToken()));
    }

    @Test
    public void registerCustomer_emailExisted_thenThrowException() {
        when(customerRepository.findByEmail(getSampleCustomer().getEmail())).thenReturn(Optional.of(getSampleCustomer()));
        Throwable exception = assertThrows(RuntimeException.class, () -> customerService.register(LoginDto.builder().email(getSampleCustomer().getEmail()).password("pass1234").build()));
        assertEquals("This email is already registered",exception.getMessage());
    }

}
