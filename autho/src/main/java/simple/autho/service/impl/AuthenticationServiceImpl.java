package simple.autho.service.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private RedisTemplate<String, Session> sRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Session CreateUser(User user) {
        User _user = userRepository.findUserByName(user.getUserName());
        if (_user != null)
        {
            return null;
        }
        try {
            Query q = entityManager.createNativeQuery("INSERT INTO public.tb_user (username, email, passwd) VALUES (?, ?, ?)");
            q.setParameter(1, user.getUserName());
            q.setParameter(2, user.getEmail());
            q.setParameter(3, user.getPassWd());
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return AuthencateUser(user.getUserName(), user.getPassWd());
    }

    @Override
    public boolean UpdateUserPasswd(Session session, String oldPassWd, String newPassWd) {
        if (CheckAuthencatition(session) != SessionStatus.Valid)
        {
            return false;
        }

        User _user = userRepository.findUserByName(session.getUserName());
        if (_user == null)
        {
            return false;
        }

        if (!_user.getPassWd().equals(oldPassWd))
        {
            return false;
        }

        _user.setPassWd(newPassWd);
        try {
            userRepository.saveAndFlush(_user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                sRedisTemplate.opsForValue()
                .set(session.getAuthencation(), session);
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
        Session _session = sRedisTemplate.opsForValue().get(session.getAuthencation());
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
                sRedisTemplate.delete(session.getAuthencation());
                sessionStatus = SessionStatus.Expired;
            }
        }
        return sessionStatus;
    }

    @Override
    public boolean Logout(Session session) {
        if (session == null)
        {
            return false;
        } else
        {
            return sRedisTemplate.delete(session.getAuthencation());
        }
    }
}