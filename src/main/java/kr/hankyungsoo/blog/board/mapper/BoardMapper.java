package kr.hankyungsoo.blog.board.mapper;

import kr.hankyungsoo.blog.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void boardSave(BoardDto boardDto);

    List<BoardDto> boardList(String title);
}
