package com.example.trellox.service;

import com.example.trellox.dto.MemberDto;
import com.example.trellox.model.Card;
import com.example.trellox.repository.BoardRepository;
import com.example.trellox.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;

    public CardServiceImpl(CardRepository cardRepository, BoardRepository boardRepository) {
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public Card saveCard(String boardId, Card card) {
        card.setBoardId(boardId);
        boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("board not found"));

        if (card.getCardTitle()== null || card.getCardTitle().isEmpty())
            throw new RuntimeException("card title is mandatory");
        return cardRepository.save(card);
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
