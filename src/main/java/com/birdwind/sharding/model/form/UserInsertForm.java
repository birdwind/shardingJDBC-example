package com.birdwind.sharding.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertForm {

    private String nickname;
    private String password;
    private int sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}
