package com.example.trellox.service;

import com.example.trellox.dto.MemberDto;
import com.example.trellox.model.Card;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Override
    public Card saveCard(String boardId, Card card) {
        return null;
    }

    @Override
    public Card updateCard(String boardId, String cardId, Card card) {
        return null;
    }

    @Override
    public void deleteCard(String cardId) {

    }

    @Override
    public List<Card> findAllCards(String boardId, String title, String members) {
        return null;
    }

    @Override
    public Card unassignCard(String boardId, String cardId, String memberEmail) {
        return null;
    }

    @Override
    public Card assignCard(String boardId, String cardId, MemberDto memberDto) {
        return null;
    }
}
