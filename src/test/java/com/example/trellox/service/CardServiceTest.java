package com.example.trellox.service;

import com.example.trellox.model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CardServiceImpl.class})
@TestPropertySource(locations = {"classpath:application.properties"})
public class CardServiceTest {

    @Autowired
    CardService cardService;
    private Card getSampleCard(){
        return Card.builder().cardTitle("AmpadaCard").boardId("boardOne").build();
    }
    @Test
    public void saveCard_returnCard(){
        Card card = cardService.saveCard("1", getSampleCard());
        assertEquals(getSampleCard().getCardTitle(),card.getCardTitle());
        assertEquals(getSampleCard().getBoardId(),card.getBoardId());
    }
}
