package com.example.trellox.controller;


import com.example.trellox.enums.Role;
import com.example.trellox.model.Board;
import com.example.trellox.model.Customer;
import com.example.trellox.security.JwtUtils;
import com.example.trellox.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {
    @Autowired
    JwtUtils jwtUtils;

    @MockBean
    BoardService boardService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    public Board getSampleBoard() {
        return Board.builder().boardName("boardOne").build();
    }

    private Customer getSampleCustomer() {
        return Customer.builder()
                .id("id-1")
                .email("a.gmail.com")
                .password("pass1234")
                .roles(List.of(Role.CUSTOMER))
                .build();
    }

    @Test
    public void createNewBoard() throws Exception {
        when(boardService.saveBoard(getSampleBoard())).thenReturn(getSampleBoard());
        mockMvc.perform(post("/api/boards")
                        .header("Authorization", "Bearer " + jwtUtils.generateToken(getSampleCustomer()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getSampleBoard())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.boardName").value(getSampleBoard().getBoardName()));
    }

    @Test
    public void createNewBoard_withoutJwt_httpStatusIsForbidden() throws Exception {
        when(boardService.saveBoard(getSampleBoard())).thenReturn(getSampleBoard());
        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getSampleBoard())))
                .andExpect(status().isForbidden());
    }
}
