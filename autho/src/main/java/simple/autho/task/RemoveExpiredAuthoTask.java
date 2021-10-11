package simple.autho.task;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import simple.autho.entity.Session;

@Component
public class RemoveExpiredAuthoTask {
    private Logger logger = LoggerFactory.getLogger(RemoveExpiredAuthoTask.class);

    @Autowired
    private RedisTemplate<String, Session> sRedisTemplate;
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        logger.info("Remove Expired Autho Task Started.");
        Set<String> keys = sRedisTemplate.keys("*");
        Iterator<String> keyIter = keys.iterator();
        Date date = new Date();
        ArrayList<String> expiredKeys = new ArrayList<String>();
        while (keyIter.hasNext())
        {
            String key = keyIter.next();
            if (!CheckKeyValid(key, date))
            {
                expiredKeys.add(key);
            }
        }
        if (!expiredKeys.isEmpty())
        {
            sRedisTemplate.delete(expiredKeys);
        }
        logger.info("Remove Expired Autho Task Finished. Removed expired key count: " + expiredKeys.size());
    }

    private boolean CheckKeyValid(String key, Date date)
    {
        Session s = sRedisTemplate.opsForValue().get(key);
        return s.getExpiredDate().after(date);
    }
}
