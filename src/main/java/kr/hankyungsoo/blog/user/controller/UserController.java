package kr.hankyungsoo.blog.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/login")
    public String loginForm(Model model){

        return "user/login";
    }

}
