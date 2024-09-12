package com.me.yaoojbackendjudgeservice.judge.controller.feignclientimpl;

import com.me.yaoojbackendjudgeservice.judge.JudgeService;
import com.me.yaoojbackendmodel.model.entity.QuestionSubmit;
import com.me.yaoojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该接口仅微服务内部调用
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/11 11:44
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@RestController
@RequestMapping("/inner")
public class JudgeFeignClientImplController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
