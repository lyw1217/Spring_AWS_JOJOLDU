package com.kcl.example.springboot.web;

import com.kcl.example.springboot.config.auth.LoginUser;
import com.kcl.example.springboot.config.auth.dto.SessionUser;
import com.kcl.example.springboot.service.posts.PostsService;
import com.kcl.example.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    // 서버 탬플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음, 여기서는 postsService.findAllDesc()로 가져온 결과를 post 로 index.mustache 에 전달함
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    // private final HttpSession  httpSession; // 반복되는 부분들은 @LoginUser 로 개선

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {     // 반복되는 부분들은 @LoginUser 로 개선
        model.addAttribute("posts", postsService.findAllDesc());

        /*
            CustomOAuth2UserService 에서 로그인 성공 시 세선에 SessionUser 를 저장하도록 구성되어있다.
            즉, 로그인 성공 시 httpSession.getAttribute("user") 에서 값을 가져올 수 있다.
         */
        // SessionUser user = (SessionUser) httpSession.getAttribute("user"); // 반복되는 부분들은 @LoginUser 로 개선

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
