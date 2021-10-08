package simple.autho.service;

import simple.autho.entity.Session;

public interface AuthenticationService {
    public boolean CreateUser(String name, String passWd);
    public boolean UpdateUserPasswd(String name, String oldPassWd, String newPassWd);
    public Session AuthencateUser(String name, String passWd);
}
