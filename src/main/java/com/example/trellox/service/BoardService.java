package com.example.trellox.service;

import com.example.trellox.model.Board;

import java.util.List;

public interface BoardService {
    Board saveBoard(Board board);

    Board updateBoard(String boardId, Board board);

    List<Board> findAllBoards();

    Board findBoardById(String boardId);

    void deleteBoard(String boardId);

}
