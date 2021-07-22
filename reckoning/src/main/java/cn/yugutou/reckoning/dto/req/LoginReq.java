package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class LoginReq implements Serializable {
    @NotBlank(message = "The user login mobileNo cannot be empty")
    private String mobileNo;
    @NotBlank(message = "The user login password cannot be empty")
    private String password;
}
