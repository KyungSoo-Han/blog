package kr.hankyungsoo.blog.board.mapper;

import kr.hankyungsoo.blog.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void boardSave(BoardDto boardDto);
}
