package com.me.yaoojbackenduserservice.controller.feignclientimpl;

import com.me.yaoojbackendmodel.model.entity.User;
import com.me.yaoojbackendserviceclient.service.UserFeignClient;
import com.me.yaoojbackenduserservice.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 该接口仅微服务内部调用
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/11 11:20
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@RestController
@RequestMapping("/inner")
@Hidden
public class UserFeignClientImplController implements UserFeignClient {

    @Resource
    private UserService userService;

    @GetMapping("/get/id")
    @Override
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @GetMapping("/get/ids")
    @Override
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
