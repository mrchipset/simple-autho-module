package simple.autho.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import simple.autho.entity.Session;
import simple.autho.entity.SessionStatus;
import simple.autho.entity.User;
import simple.autho.entity.UserRepository;
import simple.autho.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, Session> sessionRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public boolean CreateUser(String name, String passWd, Session session) {
        return false;
    }

    @Override
    public boolean UpdateUserPasswd(String name, String oldPassWd, String newPassWd, Session session) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Session AuthencateUser(String name, String passWd) {
        User _user = userRepository.findUserByName(name);
        if (_user == null)
        {
            return null;
        } else
        {
            if(passWd.equals(_user.getPassWd()))
            {
                _user.setLastLoginDate(new Date());
                userRepository.saveAndFlush(_user); // update current login time to db
                Session session = GetAuthcenticationSession(_user);
                sessionRedisTemplate.opsForValue()
                .set(session.getAuthencation(), session);
                // sessionRedisTemplate.expireAt(session.getAuthencation(), session.getExpiredDate());
                return session;
            } else
            {
                return new Session(_user.getUserName(), null, null);
            }
        }
    }

    private Session GetAuthcenticationSession(User user)
    {
        String userName = user.getUserName();
        Date loginDate = user.getLastLoginDate();
        long oneWeekMillisecs = 1000 * 3600 * 24 * 7;
        Date expireDate = new Date(loginDate.getTime() + oneWeekMillisecs);
        String buffer = userName + loginDate.toString();
        String authencatition = DigestUtils.md5DigestAsHex(buffer.getBytes());
        return new Session(userName, authencatition, expireDate);
    }

    @Override
    public SessionStatus CheckAuthencatition(Session session) {
        SessionStatus sessionStatus = SessionStatus.NotFound;
        Session _session = sessionRedisTemplate.opsForValue().get(session.getAuthencation());
        if (_session == null)
        {
            sessionStatus = SessionStatus.NotFound;
        } else
        {
            if (_session.getExpiredDate().after(new Date()))
            {
                sessionStatus = SessionStatus.Valid;
            } else
            {
                sessionRedisTemplate.delete(session.getAuthencation());
                sessionStatus = SessionStatus.Expired;
            }
        }
        return sessionStatus;
    }


}