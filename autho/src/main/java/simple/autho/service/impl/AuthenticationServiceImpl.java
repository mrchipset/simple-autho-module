package simple.autho.service.impl;

import simple.autho.entity.Session;
import simple.autho.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {


    @Override
    public boolean CreateUser(String name, String passWd) {
        Session session = new Session();
        
        return false;
    }

    @Override
    public boolean UpdateUserPasswd(String name, String oldPassWd, String newPassWd) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Session AuthencateUser(String name, String passWd) {
        // TODO Auto-generated method stub
        return null;
    }
}