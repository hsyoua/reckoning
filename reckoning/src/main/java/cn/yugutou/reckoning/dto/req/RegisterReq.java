package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class RegisterReq implements Serializable {
    @NotBlank(message = "The user name cannot be empty")
    private String userName;
    @NotBlank(message = "The user password cannot be empty")
    private String password;
    @NotBlank(message = "The user mobile no cannot be empty")
    private String mobileNo;
    private String userRemark;
}
