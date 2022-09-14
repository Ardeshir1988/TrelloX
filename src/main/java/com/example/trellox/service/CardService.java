package com.example.trellox.service;

import com.example.trellox.dto.MemberDto;
import com.example.trellox.model.Card;

import java.util.List;

public interface CardService {
    Card saveCard(String boardId, Card card);

    Card updateCard(String boardId, String cardId, Card card);

    void deleteCard(String cardId);

    List<Card> findAllCards(String boardId, String title, String members);

    Card unassignCard(String boardId, String cardId, String memberEmail);

    Card assignCard(String boardId, String cardId, MemberDto memberDto);
}
