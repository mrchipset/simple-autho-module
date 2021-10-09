package simple.autho.service;

import org.springframework.stereotype.Service;

import simple.autho.entity.Session;
import simple.autho.entity.SessionStatus;
@Service
public interface AuthenticationService {
    public boolean CreateUser(String name, String passWd, Session session);
    public boolean UpdateUserPasswd(String name, String oldPassWd, String newPassWd, Session session);
    public Session AuthencateUser(String name, String passWd);
    public SessionStatus CheckAuthencatition(Session session);
}
