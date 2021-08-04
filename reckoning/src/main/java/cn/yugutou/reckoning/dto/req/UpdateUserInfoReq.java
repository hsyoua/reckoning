package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateUserInfoReq implements Serializable {
    @NotNull(message = "The userId  cannot be null")
    private Long userId;

    private String userName;

    private String mobileNo;

    private String userRemarks;
}
