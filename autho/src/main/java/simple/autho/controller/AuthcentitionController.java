package simple.autho.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import simple.autho.service.AuthenticationService;
import simple.autho.service.impl.AuthenticationServiceImpl;

@RestController
public class AuthcentitionController {
    
    private AuthenticationService service = new AuthenticationServiceImpl();
    // @CrossOrigin
    @PostMapping("/login")
    public String login()
    {
        return "Hello, Post Login";
    }

    // @CrossOrigin
    @PostMapping("/register")
    public String register()
    {
        return "Hello, Post Register";
    }

    // @CrossOrigin
    @PostMapping("/status")
    public String status()
    {
        return "Helllo, Post Info";
    }
}
