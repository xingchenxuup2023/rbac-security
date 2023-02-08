package com.xcx.security.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author 邢晨旭
 * @date 2023/1/30
 * @description 接口统一返回封装
 */
@Getter
@Setter
@ToString
@Slf4j
public class Result<T> {

    /**
     * 结果码
     */
    private String code;

    /**
     * 结果描述
     */
    private String msg;

    /**
     * 结果数据
     */
    private T data;

    public Result<T> code(String code) {
        this.code = code;
        return this;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setMsg(ResponseEnum.OK.msg());
        result.setCode(ResponseEnum.OK.value());
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(ResponseEnum.OK.value());
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        log.error(ResponseEnum.FAIL.toString());
        result.setMsg(ResponseEnum.FAIL.msg());
        result.setCode(ResponseEnum.FAIL.value());
        return result;
    }

    public static <T> Result<T> fail(String msg) {
        log.error(msg);
        Result<T> result = new Result<>();
        result.setMsg(msg);
        result.setCode(ResponseEnum.FAIL.value());
        return result;
    }


    public static <T> Result<T> fail(ResponseEnum responseEnum) {
        log.error(responseEnum.toString());
        Result<T> result = new Result<>();
        result.setMsg(responseEnum.msg());
        result.setCode(responseEnum.value());
        return result;
    }

    /**
     * 其他接口返回的失败结果通过此方法转换之后在使用
     * @param oldResult 其他接口的返回结果
     * @return
     * @param <T>
     */
    public static <T> Result<T> transform(Result<?> oldResult) {
        Result<T> result = new Result<>();
        result.setMsg(oldResult.getMsg());
        result.setCode(oldResult.getCode());
        log.error(result.toString());
        return result;
    }

    public boolean isSuccess() {
        return Objects.equals(ResponseEnum.OK.value(), this.code);
    }

}
