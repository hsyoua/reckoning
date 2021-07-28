package cn.yugutou.reckoning.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huangsy
 * @date 2021/07/22
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    //成功提示码
    SUCCESS(20000, "成功"),

    //自定义失败信息
    FAILURE(50000, "失败"),

    //通用错误码 50001~50099
    PROGRAM_INSIDE_EXCEPTION(50001, "程序内部异常"),
    REQUEST_PARAM_ERROR(50002, "请求参数错误"),

    //用户模块错误码 50100~50199
    USER_ALREAD_EXISTS(50100,"该手机用户已存在"),
    USER_LOGIN_CHECK_FAIL(50101,"The user does not exist or the password is incorrect"),
    USER_STATUS_EXCEPTION(50102,"账户已被冻结，请修改密码！"),
    //流程模块错误码 50200~50299
    //账单模块错误码 50300~50399
    BILL_PARAMETER_BE_EMPTY(50300,"Billing participating user information cannot be blank");

    private final Integer code;
    private final String message;

}

