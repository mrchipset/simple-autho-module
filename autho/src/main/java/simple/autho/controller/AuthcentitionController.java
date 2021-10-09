package simple.autho.controller;

import java.io.Console;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import simple.autho.entity.Session;
import simple.autho.entity.SessionStatus;
import simple.autho.entity.User;
import simple.autho.service.AuthenticationService;

@RestController
public class AuthcentitionController {
    
    @Autowired
    private AuthenticationService service;
    private Logger logger = LoggerFactory.getLogger(AuthcentitionController.class);

    @ResponseBody
    @PostMapping(path="/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> login(@RequestBody User user) throws Exception
    {
        logger.debug(user.toString());
        Session session = service.AuthencateUser(user.getUserName(), user.getPassWd());
  
        if (session == null)    // no user
        {
            logger.info("UserName:" + user.getUserName() + " is NOT Existed.");
            return ResponseEntity.notFound().build();
        } else
        {
            if (session.getAuthencation() == null)  // error passWd
            {
                logger.info("UserName:" + user.getUserName() + " error passWd.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else  // ok
            {
                return ResponseEntity.ok(session);
            }
        }
    }

    @PostMapping("/register")
    public String register()
    {
        return "Hello, Post Register";
    }

    @ResponseBody
    @PostMapping(path="/check", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> check(@RequestBody Session session) throws Exception
    {
        SessionStatus status = service.CheckAuthencatition(session);
        switch (status)
        {
            case Expired:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            case NotFound:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            case Valid:
                return ResponseEntity.ok(session);
            default:
                break;
        }
        return ResponseEntity.notFound().build();
    }
}
