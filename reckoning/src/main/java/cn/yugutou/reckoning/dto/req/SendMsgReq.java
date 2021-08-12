package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author chenqun
 * @Date 2021/8/12 19:36
 * @Description TODO
 */
@Data
public class SendMsgReq implements Serializable {
    @NotBlank(message = "The user login mobileNo cannot be empty")
    private String mobileNo;
    private String code;
}
