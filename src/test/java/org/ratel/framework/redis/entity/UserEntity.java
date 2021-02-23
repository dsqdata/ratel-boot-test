package org.ratel.framework.redis.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity extends RatelNoRepeatRedisEntity {
    private Long id;
    private String userName;
    private String userSex;
    private Date createTime;
}