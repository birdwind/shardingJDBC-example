package com.birdwind.sharding.model.service;

import com.birdwind.sharding.model.PO.UserPO;
import com.birdwind.sharding.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public int insert(UserPO userPO) {
        return userMapper.insert(userPO);
    }

    public List<UserPO> select() {
        return userMapper.select();
    }
}
