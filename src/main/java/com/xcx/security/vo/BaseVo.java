package com.xcx.security.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 邢晨旭
 * @date 2023/2/1
 */
@Getter
@Setter
@ToString
public class BaseVo {
    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建时间
     */
    protected Long createAccount;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 更新时间
     */
    protected Long updateAccount;
}
