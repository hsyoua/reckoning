package cn.yugutou.reckoning.dto.resp;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginResp implements Serializable {
    private String userName;
    private String userId;
    private String userRemark;
}
