package com.example.trellox.service;

import com.example.trellox.model.Board;
import com.example.trellox.model.Card;
import com.example.trellox.repository.BoardRepository;
import com.example.trellox.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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

    private Card getSampleCard() {
        return Card.builder().cardTitle("AmpadaCard").boardId("boardOne").build();
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
}
