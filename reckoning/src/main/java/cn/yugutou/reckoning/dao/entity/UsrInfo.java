package cn.yugutou.reckoning.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UsrInfo {
    private Long userId;
    private String password;
    private String userName;
    private String mobileNo;
    private String userRole;
    private String userRemarks;
    private Date lastLoginTime;
    private Date createTime;
    private Date modifyTime;
    private Integer pageNo;
    private Integer pageSize;
    private Integer  totalNum;
    private int passwordErrorNum;
    private String userStatus;
}
