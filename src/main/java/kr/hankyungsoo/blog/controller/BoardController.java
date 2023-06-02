package kr.hankyungsoo.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {


    @GetMapping("/boardWrite")
    public String boardForm(Model model) {
       //String id = session.getAttribute("user_id") == null ? (String) session.getAttribute("user_id") : "";

        return "board/boardWriteForm";
    }

    @GetMapping("/board")
    public String list() {
        return "board/boardList";
    }
}
