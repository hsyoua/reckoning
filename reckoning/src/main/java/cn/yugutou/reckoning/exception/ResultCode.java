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
    USER_OLDPASSWORD_ERROR(50103,"原始密码输入错误，请重新输入"),
    USER_NEWPASSWORD_ERROR(50104,"新密码与原始密码相同，无法修改"),
    USER_CONFIRMPASSWORD_ERROR(50105,"确认密码与新密码不一致，请修改"),
    user_pagesize_max(50106,"查询失败，查询条数最多50条"),
    //流程模块错误码 50200~50299
    //账单模块错误码 50300~50399
    BILL_PARAMETER_BE_EMPTY(50300,"Billing participating user information cannot be blank"),
    FIND_BILL_DETAIL_ERROR(50301,"您未参与该账单，无法查看");
    private final Integer code;
    private final String message;

}

