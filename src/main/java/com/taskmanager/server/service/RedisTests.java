package com.taskmanager.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;


}
