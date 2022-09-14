package com.example.trellox.service.impl;

import com.example.trellox.dto.MemberDto;
import com.example.trellox.model.Card;
import com.example.trellox.repository.BoardRepository;
import com.example.trellox.repository.CardRepository;
import com.example.trellox.repository.CustomerRepository;
import com.example.trellox.service.CardService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final CustomerRepository customerRepository;

    public CardServiceImpl(CardRepository cardRepository,
                           BoardRepository boardRepository,
                           CustomerRepository customerRepository) {
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Card saveCard(String boardId, Card card) {
        card.setBoardId(boardId);
        boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("board not found"));

        if (card.getCardTitle() == null || card.getCardTitle().isEmpty())
            throw new RuntimeException("card title is mandatory");
        card.setMembers(Set.of());
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(String boardId, String cardId, Card card) {
        Card foundCard = findCardByIdAndBoardId(cardId, boardId);
        foundCard.setCardTitle(card.getCardTitle());
        foundCard.setMembers(card.getMembers());
        foundCard.setBoardId(boardId);
        return cardRepository.save(foundCard);
    }

    @Override
    public void deleteCard(String cardId) {
        cardRepository.deleteById(cardId);
    }


    @Override
    public List<Card> findAllCards(String boardId, String title, String members) {
        if (title != null && !title.isEmpty())
            return cardRepository.findAllByBoardIdAndCardTitleStartingWith(boardId, title);
        else if (members != null && !members.isEmpty())
            return cardRepository.findAllByBoardIdAndMembersIn(boardId, Arrays.asList(members.split(",")));
        else return cardRepository.findAllByBoardIdOrderByModifiedOnDesc(boardId);
    }


    @Override
    public Card unassignCard(String boardId, String cardId, String memberEmail) {
        Card card = findCardByIdAndBoardId(cardId, boardId);
        card.setMembers(card.getMembers().stream().filter(email -> !email.equals(memberEmail)).collect(Collectors.toSet()));
        return cardRepository.save(card);
    }

    @Override
    public Card assignCard(String boardId, String cardId, MemberDto memberDto) {
        Card card = findCardByIdAndBoardId(cardId, boardId);
        Set<String> members = card.getMembers();
        memberDto.getEmails().forEach(email -> {
            customerRepository.findByEmail(email)
                    .ifPresent(customer -> members.add(customer.getEmail()));
        });
        card.setMembers(members);
        return cardRepository.save(card);
    }

    private Card findCardByIdAndBoardId(String cardId, String boardId) {
        return cardRepository.findByIdAndBoardId(cardId, boardId)
                .orElseThrow(() -> new RuntimeException("card not found"));
    }
}
