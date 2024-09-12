package com.me.yaoojbackendquestionservice.controller.feignclientimpl;

import com.me.yaoojbackendmodel.model.entity.Question;
import com.me.yaoojbackendmodel.model.entity.QuestionSubmit;
import com.me.yaoojbackendquestionservice.service.QuestionService;
import com.me.yaoojbackendquestionservice.service.QuestionSubmitService;
import com.me.yaoojbackendserviceclient.service.QuestionFeignClient;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该接口仅微服务内部调用
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/11 11:36
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@RestController
@RequestMapping("/inner")
@Hidden
public class QuestionFeignClientImplController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
