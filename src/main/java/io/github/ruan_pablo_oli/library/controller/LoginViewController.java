package io.github.ruan_pablo_oli.library.controller;


import io.github.ruan_pablo_oli.library.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        if(authentication instanceof CustomAuthentication customAuthentication){
            return authentication.getName();
        }
        return "Olá " + authentication.getName();
    }

    @GetMapping("/authorized")
    public String getAuthorizedCode(@RequestParam("code") String code){
        return "Seu authorization code: " + code;
    }
}
