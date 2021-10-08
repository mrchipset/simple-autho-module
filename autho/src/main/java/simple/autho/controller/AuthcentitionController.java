package simple.autho.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import simple.autho.service.AuthenticationService;
import simple.autho.service.impl.AuthenticationServiceImpl;

@RestController
public class AuthcentitionController {
    
    private AuthenticationService service = new AuthenticationServiceImpl();
    @GetMapping("/info")
    public String get()
    {
        return "Hello, Get Info";
    }

    @PostMapping("/info")
    public String post()
    {
        return "Hello, Post Info";
    }
}
