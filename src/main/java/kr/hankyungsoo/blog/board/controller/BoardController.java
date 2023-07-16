package kr.hankyungsoo.blog.board.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import kr.hankyungsoo.blog.board.dto.BoardDto;
import kr.hankyungsoo.blog.board.service.BoardService;
import kr.hankyungsoo.blog.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
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
    public String boardUpdate(@ModelAttribute BoardDto boardDto,@RequestParam MultipartFile file,  BindingResult bindingResult) throws IOException {

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

        boardDto.setOrgFileName(file.getOriginalFilename());
        boardDto.setSrvFileName(fileService.uploadFile(file));

        boardService.boardUpdate(boardDto);

        //return "redirect:/board/"+boardDto.getId();

        return "redirect:/board";
    }

    @ResponseBody
    @GetMapping("/board/download/v1/{filename}")
    public Resource downloadFile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @GetMapping("/board/download/v2/{boardId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long boardId) throws MalformedURLException {
        BoardDto board = boardService.getBoard(boardId);
        String srvFileName = board.getSrvFileName();
        String orgFileName = board.getOrgFileName();

        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(srvFileName));

        String encodedUploadFileName = UriUtils.encode(orgFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
    @GetMapping("/board/filedelete/{boardId}")
    public String deleteFile(@PathVariable Long boardId) throws Exception {
        File file;
        try {
            BoardDto board = boardService.getBoard(boardId);
            String srvFileName = board.getSrvFileName();
            String orgFileName = board.getOrgFileName();

            boardService.fileClear(board);

            file = new File(fileService.getFullPath(srvFileName));
            if(file.exists())
                file.delete();

        }
        catch (Exception err){
            log.info("err={}",err.getMessage());
            throw new Exception( err.getMessage());
        }

        return "redirect:/board/"+boardId;
    }
}
