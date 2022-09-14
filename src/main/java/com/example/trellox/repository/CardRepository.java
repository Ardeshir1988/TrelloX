package com.example.trellox.repository;

import com.example.trellox.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends MongoRepository<Card,String> {
    List<Card> findAllByBoardIdOrderByModifiedOnDesc(String boardId);
    List<Card> findAllByBoardIdAndMembersIn(String boardId,List<String> members);
    List<Card> findAllByBoardIdAndCardTitleStartingWith(String boardId,String cardTitle);
    Optional<Card> findByIdAndBoardId(String cardId,String boardId);
}
