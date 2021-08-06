package cn.yugutou.reckoning.dto.resp;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginResp implements Serializable {
    private String userName;
    private Long userId;
    private String userRemarks;
    private Integer errorNum;
    private String token;
}
