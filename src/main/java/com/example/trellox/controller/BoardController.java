package com.example.trellox.controller;

import com.example.trellox.dto.MemberDto;
import com.example.trellox.model.Board;
import com.example.trellox.model.Card;
import com.example.trellox.service.BoardService;
import com.example.trellox.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@PreAuthorize(value = "hasAuthority('CUSTOMER')")
public class BoardController {
    private final BoardService boardService;
    private final CardService cardService;

    public BoardController(BoardService boardService, CardService cardService) {
        this.boardService = boardService;
        this.cardService = cardService;
    }

    @GetMapping()
    public List<Board> findAllBoards() {
        return boardService.findAllBoards();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Board saveNewBoard(@RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    @PutMapping("/{boardId}")
    public Board updateBoard(
            @PathVariable String boardId,
            @RequestBody Board board) {
        return boardService.updateBoard(boardId, board);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoard(boardId);
    }

    @PostMapping("/{boardId}/cards")
    public Card saveNewCard(@PathVariable String boardId,
                            @RequestBody Card card) {
        return cardService.saveCard(boardId, card);
    }

    @PutMapping("/{boardId}/cards/{cardId}")
    public Card saveNewCard(@PathVariable String boardId,
                            @PathVariable String cardId,
                            @RequestBody Card card) {
        return cardService.updateCard(boardId, cardId, card);
    }

    @PutMapping("/{boardId}/cards/{cardId}/members/{memberEmail}/unassign")
    public Card unassignCard(@PathVariable String boardId,
                            @PathVariable String cardId,
                            @PathVariable String memberEmail) {
        return cardService.unassignCard(boardId, cardId, memberEmail);
    }

    @PutMapping("/{boardId}/cards/{cardId}/members")
    public Card assignCard(@PathVariable String boardId,
                             @PathVariable String cardId,
                             @Validated @RequestBody MemberDto memberDto) {
        return cardService.assignCard(boardId, cardId, memberDto);
    }

    @GetMapping("/{boardId}/cards")
    public List<Card> findAllCards(@PathVariable String boardId,
                                   @RequestParam(value = "members", required = false) String members,
                                   @RequestParam(value = "title", required = false) String title) {
        return cardService.findAllCards(boardId,title, members);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{boardId}/cards/{cardId}")
    public void deleteCardById(@PathVariable String cardId,
                               @PathVariable String boardId) {
        cardService.deleteCard(cardId);
    }
}
