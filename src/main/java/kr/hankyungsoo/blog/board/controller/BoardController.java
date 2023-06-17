package kr.hankyungsoo.blog.board.controller;

import kr.hankyungsoo.blog.board.dto.BoardDto;
import kr.hankyungsoo.blog.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boardWrite/{id}")
    public String boardForm(Model model, @PathVariable(required = false) Long id) {
       //String id = session.getAttribute("user_id") == null ? (String) session.getAttribute("user_id") : "";
        BoardDto board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/boardWriteForm";
    }

    @GetMapping("/board")
    public String boardList(Model model) {
        List<BoardDto> board = boardService.boardList("");
        model.addAttribute("board",board);
        return "board/boardList";
    }

    @PostMapping("/boardWrite")
    public String boardSave(@ModelAttribute BoardDto boardDto, RedirectAttributes redirectAttributes, Model model){
        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(boardDto.getTitle())) {
            errors.put("title", "제목은 필수입니다.");
        }

        if(!StringUtils.hasText(boardDto.getContent())) {
            errors.put("content", "내용은 필수입니다.");
        }

        if (!errors.isEmpty()) {
            log.info("errors = {} ", errors);
            model.addAttribute("errors", errors);
            return "board/boardWriteForm";
        }
        boardService.boardSave(boardDto);

        return "board/boardWriteForm";
    }
}
