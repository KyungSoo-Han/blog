package kr.hankyungsoo.blog.board.mapper;

import com.github.pagehelper.Page;
import kr.hankyungsoo.blog.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void boardInsert(BoardDto boardDto);
    void boardUpdate(BoardDto boardDto);
    BoardDto getBoard (Long id);
    Page<BoardDto> boardList(String title);

    void fileClear(BoardDto boardDto);

}
