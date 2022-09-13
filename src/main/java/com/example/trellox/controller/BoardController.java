package com.example.trellox.controller;

import com.example.trellox.model.Board;
import com.example.trellox.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public List<Board> findAllBoards() {
        return boardService.findAllBoards();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/boards")
    public Board saveNewBoard(@RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    @PutMapping("/boards/{boardId}")
    public Board updateBoard(
            @PathVariable String boardId,
            @RequestBody Board board) {
        return boardService.updateBoard(boardId, board);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/boards/{boardId}")
    public void deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoard(boardId);
    }

}
