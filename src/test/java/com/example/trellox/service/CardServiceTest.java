package com.example.trellox.service;

import com.example.trellox.dto.MemberDto;
import com.example.trellox.model.Board;
import com.example.trellox.model.Card;
import com.example.trellox.repository.BoardRepository;
import com.example.trellox.repository.CardRepository;
import com.example.trellox.repository.CustomerRepository;
import com.example.trellox.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CardServiceImpl.class})
@TestPropertySource(locations = {"classpath:application.properties"})
public class CardServiceTest {

    @Autowired
    CardService cardService;
    @MockBean
    CardRepository cardRepository;
    @MockBean
    BoardRepository boardRepository;
    @MockBean
    CustomerRepository customerRepository;

    private Card getSampleCard() {
        return Card.builder().cardTitle("AmpadaCard").boardId("boardOne").members(Set.of()).build();
    }

    @Test
    public void saveCard_returnCard() {
        when(cardRepository.save(getSampleCard())).thenReturn(Card.builder().id("1").cardTitle(getSampleCard().getCardTitle()).boardId(getSampleCard().getBoardId()).build());
        when(boardRepository.findById(getSampleCard().getBoardId())).thenReturn(Optional.of(Board.builder().id(getSampleCard().getBoardId()).build()));
        Card card = cardService.saveCard(getSampleCard().getBoardId(), getSampleCard());
        assertEquals(getSampleCard().getCardTitle(), card.getCardTitle());
        assertEquals(getSampleCard().getBoardId(), card.getBoardId());
    }

    @Test
    public void saveCard_notExistedBoardId_thenThrowException() {
        when(boardRepository.findById(getSampleCard().getBoardId())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(RuntimeException.class, () -> cardService.saveCard(getSampleCard().getBoardId(), getSampleCard()));
        assertEquals("board not found", exception.getMessage());
    }

    @Test
    public void updateCard_retrunUpdatedCard() {
        when(cardRepository.findByIdAndBoardId("1", getSampleCard().getBoardId())).thenReturn(Optional.of(getSampleCard()));
        Card updatedCart = getSampleCard();
        updatedCart.setId("1");
        when(cardRepository.save(getSampleCard())).thenReturn(updatedCart);
        Card card = cardService.updateCard(getSampleCard().getBoardId(), "1", getSampleCard());
        assertEquals("1", card.getId());
        assertEquals(getSampleCard().getBoardId(), card.getBoardId());
    }

    @Test
    public void assignCard_retrunCard_with_exactMember() {
        when(cardRepository.findByIdAndBoardId("1", getSampleCard().getBoardId())).thenReturn(Optional.of(getSampleCard()));
        Card sampleCard = getSampleCard();
        List<String> members = new ArrayList<>(sampleCard.getMembers());
        members.add("a@gmail.com");
        sampleCard.setMembers(new HashSet<>(members));
        when(cardRepository.save(getSampleCard())).thenReturn(sampleCard);
        Card card = cardService.assignCard(getSampleCard().getBoardId(), "1", MemberDto.builder().emails(List.of("a@gmail.com")).build());
        assertEquals(1, card.getMembers().size());
        assertEquals(sampleCard.getMembers().stream().findFirst().get(), card.getMembers().stream().findFirst().get());
    }

    @Test
    public void unassignCard_returnCardWithoutSpecificMember() {
        Card sampleCard = getSampleCard();
        sampleCard.setMembers(Set.of("a@gmail.com"));
        when(cardRepository.findByIdAndBoardId("1", getSampleCard().getBoardId())).thenReturn(Optional.of(sampleCard));
        when(cardRepository.save(getSampleCard())).thenReturn(getSampleCard());
        Card card = cardService.unassignCard(sampleCard.getBoardId(), "1", sampleCard.getMembers().stream().findFirst().get());
        assertEquals(Optional.empty(), card.getMembers().stream().filter(s -> s.equals("a@gmail.com")).findFirst());
    }

}
