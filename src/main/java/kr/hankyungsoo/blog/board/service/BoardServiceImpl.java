package kr.hankyungsoo.blog.board.service;

import kr.hankyungsoo.blog.board.dto.BoardDto;
import kr.hankyungsoo.blog.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    public void boardSave(BoardDto boardDto) {
        boardMapper.boardSave(boardDto);
    }

    @Override
    public BoardDto getBoard(Long id) {
        return boardMapper.getBoard(id);
    }

    @Override
    public List<BoardDto> boardList(String title) {
        return boardMapper.boardList(title);
    }
}
