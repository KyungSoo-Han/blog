package kr.hankyungsoo.blog.board.service;

import kr.hankyungsoo.blog.board.dto.BoardDto;

import java.util.List;

public interface BoardService {
    void boardSave(BoardDto boardDto);
    List<BoardDto> boardList(String title);
    BoardDto getBoard (Long id);
}
