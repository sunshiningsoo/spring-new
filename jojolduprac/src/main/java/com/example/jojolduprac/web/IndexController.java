package com.example.jojolduprac.web;

import com.example.jojolduprac.config.auth.LoginUser;
import com.example.jojolduprac.config.auth.dto.SessionUser;
import com.example.jojolduprac.domain.user.User;
import com.example.jojolduprac.service.posts.PostsService;
import com.example.jojolduprac.web.dto.PostsResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String indexPage(Model model, @LoginUser SessionUser user) { // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
        model.addAttribute("posts", postsService.findOrderDesc());
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        @LoginUser가 생기고 이제 어느 컨트롤러든지 @LoginUser만 사용하면 세션정보를 가져올 수 있게 된다.
//        세션은 내장 톰캣의 메모리에 저장되ㅗㄱ 있음, 이는 두개이상의 서버가 동작한다면, 세션마다 동기화를 해줘야 하는 문제가 있음

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index"; // -> View Resolver가 처리함, URL 요청의 결과를 전달할 타입과 값을 지정하는 관리자격임
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }


}
