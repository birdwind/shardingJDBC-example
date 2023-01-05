package com.birdwind.sharding;

import com.birdwind.sharding.model.PO.UserPO;
import com.birdwind.sharding.model.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void insertUser() {
        UserPO userPO = UserPO.builder().nickname("test").sex(1).password("123456").birthday(new Date()).brand("A").build();
        userService.insert(userPO);
        userService.insert(userPO);
        userService.insert(userPO);
        List<UserPO> userPOList = userService.select();
        log.info("output: {}", userPOList);
    }
}
