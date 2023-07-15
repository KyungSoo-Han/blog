package kr.hankyungsoo.blog.board.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import kr.hankyungsoo.blog.board.dto.BoardDto;
import kr.hankyungsoo.blog.board.service.BoardService;
import kr.hankyungsoo.blog.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping("/boardInsert")
    public String boardForm(Model model) {

        model.addAttribute("board", new BoardDto());
        return "board/boardInsertForm";
    }
    @GetMapping("/board/{id}")
    public String boardForm(Model model, @PathVariable(required = false) Long id) {
        BoardDto board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/boardUpdateForm";
    }

    @GetMapping(value = {"/board"})
    public String boardList( Model model, @RequestParam(required = false) Integer pageNum) {

        if(pageNum == null)
            pageNum = 1;
        log.info(pageNum.toString());
        PageInfo<BoardDto> board = new PageInfo<>(boardService.boardList(pageNum, ""));
        log.info(board.toString());
        model.addAttribute("board",board);
        return "board/boardList";
    }

    @PostMapping("/boardInsert")
    public String boardInsert(@ModelAttribute BoardDto boardDto, @RequestParam MultipartFile file, BindingResult bindingResult) throws IOException {

        if(!StringUtils.hasText(boardDto.getTitle())) {
            bindingResult.addError(new FieldError("boardDto","title","제목은 필수입니다."));
        }

        if(!StringUtils.hasText(boardDto.getContent())) {
            bindingResult.addError(new FieldError("boardDto","content","내용은 필수입니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "board/boardInsertForm";
        }

        boardDto.setOrgFileName(file.getOriginalFilename());
        boardDto.setSrvFileName(fileService.uploadFile(file));

        boardService.boardInsert(boardDto);

        return "redirect:/board";
    }
    @PostMapping("/boardUpdate")
    public String boardUpdate(@ModelAttribute BoardDto boardDto, BindingResult bindingResult){

        if(!StringUtils.hasText(boardDto.getTitle())) {
            bindingResult.addError(new FieldError("boardDto","title","제목은 필수입니다."));
        }

        if(!StringUtils.hasText(boardDto.getContent())) {
            bindingResult.addError(new FieldError("boardDto","content","내용은 필수입니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "board/boardUpdateForm";
        }
        boardService.boardUpdate(boardDto);

        //return "redirect:/board/"+boardDto.getId();

        return "redirect:/board";
    }
}
