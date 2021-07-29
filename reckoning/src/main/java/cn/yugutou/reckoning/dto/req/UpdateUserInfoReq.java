package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdateUserInfoReq implements Serializable {
    private Long userId;
    @NotBlank(message = "The userName cannot be empty")
    private String userName;
    @NotBlank(message = "The mobileNo  cannot be empty")
    private String mobileNo;
    @NotBlank(message = "The userRemarks  cannot be empty")
    private String userRemarks;
}
