package cn.yugutou.reckoning.dto.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserResp implements Serializable {
    private  Long userId;
    private String userName;
    private String userRemarks;
    private  String mobileNo;

}
