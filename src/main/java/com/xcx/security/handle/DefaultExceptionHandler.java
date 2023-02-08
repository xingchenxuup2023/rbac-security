package com.xcx.security.handle;

import com.xcx.security.common.ResponseEnum;
import com.xcx.security.common.Result;
import com.xcx.security.common.exception.RbacException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xcx
 * @date 2023/2/7 16:26
 */
@RestController
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(RbacException.class)
    public Result mall4cloudExceptionHandler(RbacException e) {
        ResponseEnum responseEnum = e.getResponseEnum();
        // 失败返回失败消息 + 状态码
        if (responseEnum != null) {
            return Result.fail(responseEnum);
        }
        // 失败返回消息 状态码固定为直接显示消息的状态码
        return Result.fail(e.getMessage());
    }

}
