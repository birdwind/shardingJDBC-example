package com.birdwind.sharding.model.mapper;

import com.birdwind.sharding.model.PO.UserPO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

//@Mapper
public interface UserMapper {
    int insert(UserPO userPO);

    List<UserPO> select();
}
