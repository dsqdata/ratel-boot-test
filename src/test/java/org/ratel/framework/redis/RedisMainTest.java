package org.ratel.framework.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ratel.AppRun;
import org.ratel.framework.redis.entity.UserEntity;
import org.ratel.framework.redis.template.RatelRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class RedisMainTest {

    @Autowired
    @Qualifier("ratelLogRedisTemplate")
    private RatelRedisTemplate strRedisTemplate;

    @Test
    public void testString() {
        Set list = strRedisTemplate.scan(UserEntity.class.getSimpleName(), key -> strRedisTemplate.get(key));
        Iterator<UserEntity> it = list.iterator();
        while (it.hasNext()) {
            UserEntity user2 = it.next();
            System.out.println("user:" + user2.getKey() + "," + user2.getUserName() + "," + user2.getUserSex());
            System.out.println("Create-Time:" + user2.getCreateTime());
        }
    }

    @Test
    public void testSerializable() {
        for (int i = 0; i < 100; i++) {
            UserEntity user = new UserEntity();
            user.setId(1L);
            user.setUserName("朝雾轻寒" + i);
            user.setUserSex("男");
            user.setCreateTime(new Date());
            strRedisTemplate.set(user);
            UserEntity user2 = (UserEntity) strRedisTemplate.get(user.getKey());
            System.out.println("user:" + user2.getId() + "," + user2.getUserName() + "," + user2.getUserSex());
            System.out.println("Create-Time:" + user2.getCreateTime());
        }
    }

}
