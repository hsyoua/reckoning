package cn.yugutou.reckoning.common;

import cn.yugutou.reckoning.exception.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangsy
 * @date 2021/05/12
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8037171286104362012L;

    private Integer code;

    private String message;

    private T data;

    /**
     * 成功
     */
    public static Result<Void> success() {
        Result<Void> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功，有返回数据
     */
    public static <V> Result<V> success(V data) {
        Result<V> result = new Result<>();
        result.code = ResultCode.SUCCESS.getCode();
        result.message = ResultCode.SUCCESS.getMessage();
        result.data = data;
        return result;
    }

    /**
     * 失败
     */
    public static Result<Void> failure() {
        Result<Void> result = new Result<>();
        result.setCode(ResultCode.FAILURE.getCode());
        result.setMessage(ResultCode.FAILURE.getMessage());
        return result;
    }

    /**
     * 失败，自定义失败信息
     */
    public static Result<Void> failure(String message) {
        Result<Void> result = new Result<>();
        result.setCode(ResultCode.FAILURE.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 失败，使用已定义枚举
     */
    public static Result<Void> failure(ResultCode resultCode) {
        Result<Void> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

}
