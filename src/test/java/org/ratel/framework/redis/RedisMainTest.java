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
        }
    }

    @Test
    public void testSerializable() {
        UserEntity user = new UserEntity();
//        user.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setId(1L);
        user.setUserName("朝雾轻寒");
        user.setUserSex("男");
        strRedisTemplate.set(user);
        UserEntity user2 = (UserEntity) strRedisTemplate.get(user.getKey());
        System.out.println("user:" + user2.getId() + "," + user2.getUserName() + "," + user2.getUserSex());
    }

}
