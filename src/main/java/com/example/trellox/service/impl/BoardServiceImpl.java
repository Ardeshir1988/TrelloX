package com.example.trellox.service.impl;

import com.example.trellox.model.Board;
import com.example.trellox.repository.BoardRepository;
import com.example.trellox.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board saveBoard(Board board) {
        return boardRepository.save(Board.builder().boardName(board.getBoardName()).build());
    }

    @Override
    public Board updateBoard(String boardId, Board board) {
        Board foundBoard = findBoardById(boardId);
        foundBoard.setBoardName(board.getBoardName());
        return boardRepository.save(foundBoard);
    }

    @Override
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public Board findBoardById(String boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("board not found"));
    }

    @Override
    public void deleteBoard(String boardId) {
        boardRepository.deleteById(boardId);
    }
}
