package com.example.jojolduprac.web;

import com.example.jojolduprac.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
// JSON을 반환하는 컨트롤러로 만들어줌 -> @ResponseBody를 각 메서드마다 선언한 것을 한번에 사용할 수 있게 함

public class HelloController {

    @GetMapping("/hello")
//    @RequestMapping(method = RequestMethod.GET) // 위와 동일한 역할을 한다
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, // 외부에서 api로 넘긴 파라미터를 가져오는 어노테이션임
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
