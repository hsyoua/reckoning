package cn.yugutou.reckoning.exception;

import cn.yugutou.reckoning.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * 全局异常统一处理
 *
 * @author wangbo
 * @date 2021/05/12
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception e) {
        log.error("通用异常处理", e);
        return Result.failure(ResultCode.PROGRAM_INSIDE_EXCEPTION);
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(CustomException.class)
    public Result<Void> customExceptionHandler(CustomException e) {
        log.info("自定义异常处理");
        return Result.failure(e.getResultCode());
    }

    /**
     * 参数校验结果异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("参数校验结果异常处理");
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        assert fieldError != null;
        return Result.failure(fieldError.getField() + " : " + fieldError.getDefaultMessage());
    }

    /**
     * 参数校验过程异常处理
     */
    @ExceptionHandler(ValidationException.class)
    public Result<Void> validationExceptionHandler(ValidationException e) {
        log.error("参数校验过程异常处理", e);
        return Result.failure(ResultCode.PROGRAM_INSIDE_EXCEPTION);
    }

}
