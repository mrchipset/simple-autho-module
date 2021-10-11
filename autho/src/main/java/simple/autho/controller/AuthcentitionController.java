package simple.autho.controller;

import java.io.Console;
import java.util.List;

import javax.print.attribute.standard.Media;

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

import lombok.Data;
import simple.autho.entity.Session;
import simple.autho.entity.SessionStatus;
import simple.autho.entity.User;
import simple.autho.service.AuthenticationService;

@Data
class UpdatePassWd
{
    private String oldPassWd;
    private String newPassWd;
    private Session session;
}

@RestController
public class AuthcentitionController {
    
    @Autowired
    private AuthenticationService service;
    private Logger logger = LoggerFactory.getLogger(AuthcentitionController.class);


    @ResponseBody
    @PostMapping(path="/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> login(@RequestBody User user) throws Exception
    {
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

    @ResponseBody
    @PostMapping(path="logout", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout(@RequestBody Session session) throws Exception
    {
        if (!service.Logout(session))
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping(path="/register", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> register(@RequestBody User user)
    {
        try {
            Session session = service.CreateUser(user);
            if (session == null)
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else
            {
                return ResponseEntity.ok(session);
            }
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        
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

    @ResponseBody
    @PostMapping(path="/updatepasswd", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> updatePassWd(@RequestBody UpdatePassWd updatePassWd) throws Exception
    {
        if(!service.UpdateUserPasswd(updatePassWd.getSession(),
                            updatePassWd.getOldPassWd(),
                            updatePassWd.getNewPassWd()))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } 
        return ResponseEntity.ok().build();
    }
}
