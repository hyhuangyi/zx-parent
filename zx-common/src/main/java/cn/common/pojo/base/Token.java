package cn.common.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangYi on 2018/9/5
 **/
@Data
public class Token implements Serializable {
    private String uuid;
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private String token;
    private String[] roles = new String[0];
}
