package com.sse.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 9:28
 */

@Data
@Builder
public class User {
    private String uid;
    private String username;
    private String password;
    private String email;
    private String telphone;
    private Date birthday;

}
