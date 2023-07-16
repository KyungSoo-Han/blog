package kr.hankyungsoo.blog.board.service;

import com.github.pagehelper.Page;
import kr.hankyungsoo.blog.board.dto.BoardDto;

import java.util.List;

public interface BoardService {
    void boardInsert(BoardDto boardDto);
    void boardUpdate(BoardDto boardDto);
    Page<BoardDto> boardList(int pageNum, String title);
    BoardDto getBoard (Long id);

    void fileClear(BoardDto boardDto);
}
