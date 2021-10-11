package simple.autho.service;

import org.springframework.stereotype.Service;

import simple.autho.entity.Session;
import simple.autho.entity.SessionStatus;
import simple.autho.entity.User;

@Service
public interface AuthenticationService {
    public Session CreateUser(User user);
    public boolean UpdateUserPasswd(Session session, String oldPassWd, String newPassWd);
    public Session AuthencateUser(String name, String passWd);
    public boolean Logout(Session session);
    public SessionStatus CheckAuthencatition(Session session);
}
