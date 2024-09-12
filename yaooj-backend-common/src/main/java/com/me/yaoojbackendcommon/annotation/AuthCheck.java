package com.me.yaoojbackendcommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 *
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/10 14:27
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须有某个角色
     *
     * @return
     */
    String mustRole() default "";

}

