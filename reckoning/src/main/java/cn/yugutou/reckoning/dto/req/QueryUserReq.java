package cn.yugutou.reckoning.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class QueryUserReq implements Serializable {
    @NotBlank(message = "The keyWord login mobileNo cannot be empty")
    private String keyWord;
    //起始页数
    @NotNull(message = "The pageNo login mobileNo cannot be empty")
    private Integer pageNo;
    //条数
    @NotNull(message = "The pageSize login mobileNo cannot be empty")
    private Integer pageSize;
}
