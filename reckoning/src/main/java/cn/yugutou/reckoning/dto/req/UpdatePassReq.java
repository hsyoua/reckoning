package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdatePassReq implements Serializable {
    @NotBlank(message = "The oldPass login mobileNo cannot be empty")
    private String oldPass;
    @NotBlank(message = "The newPass login mobileNo cannot be empty")
    private String newPass;
    @NotBlank(message = "The confirmNesPass login mobileNo cannot be empty")
    private String confirmNesPass;

    //用户id 入参 获取原始密码
    @NotNull (message = "The userId login mobileNo cannot be empty")
    private Long userId;
}
