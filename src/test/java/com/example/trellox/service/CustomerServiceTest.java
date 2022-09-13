package com.example.trellox.service;

import com.example.trellox.enums.Role;
import com.example.trellox.model.Customer;
import com.example.trellox.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private Customer getSampleCustomer() {
        return Customer.builder()
                .id("id-1")
                .email("a.gmail.com")
                .password("")
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
}
