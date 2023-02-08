package com.xcx.security.common.exception;

import com.xcx.security.common.ResponseEnum;
import lombok.Getter;

/**
 * @author xcx
 * @date 2023/2/7 16:25
 */
@Getter
public class RbacException extends RuntimeException{

    public ResponseEnum responseEnum;

    public RbacException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }
    public RbacException(String err) {
        super(err);
    }
}
