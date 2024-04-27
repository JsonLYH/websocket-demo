package com.example.websocketdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/cookie")
public class CookieTestController {
    @GetMapping("test")
    public Object cookieTest(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie=new Cookie("test","test");
        //注意要设置为网站的根域或对应的域名
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/");
        response.addCookie(cookie);
        return new HashMap<String,Object>(){{
           put("code",200);
        }};
    }
}
