package com.birdwind.sharding.model.PO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO implements Serializable {

    private Long id;
    private String nickname;
    private String password;
    private int sex;
    private Date birthday;
    private String brand;
}
