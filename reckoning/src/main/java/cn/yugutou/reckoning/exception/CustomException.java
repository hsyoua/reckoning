package cn.yugutou.reckoning.exception;

import lombok.Getter;

/**
 * 自定义异常
 * @auther huangsy
 * @date 2021-07-21
 */
@Getter
public class CustomException extends RuntimeException{

    private final ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

}
