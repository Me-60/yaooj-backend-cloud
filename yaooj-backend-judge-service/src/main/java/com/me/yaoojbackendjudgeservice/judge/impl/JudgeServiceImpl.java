package com.me.yaoojbackendjudgeservice.judge.impl;

import cn.hutool.json.JSONUtil;
import com.me.yaoojbackendcommon.common.ErrorCode;
import com.me.yaoojbackendcommon.exception.BusinessException;
import com.me.yaoojbackendjudgeservice.judge.JudgeService;
import com.me.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.me.yaoojbackendjudgeservice.judge.codesandbox.factory.CodeSandboxFactory;
import com.me.yaoojbackendjudgeservice.judge.codesandbox.proxy.CodeSandboxProxy;
import com.me.yaoojbackendjudgeservice.judge.strategy.manager.JudgeManager;
import com.me.yaoojbackendjudgeservice.judge.strategy.model.JudgeContext;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.me.yaoojbackendmodel.model.codesandbox.JudgeInfo;
import com.me.yaoojbackendmodel.model.dto.question.JudgeCase;
import com.me.yaoojbackendmodel.model.entity.Question;
import com.me.yaoojbackendmodel.model.entity.QuestionSubmit;
import com.me.yaoojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.me.yaoojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Value("${codesandbox.type:example}")
    private String codeSandboxType;

    @Resource
    private JudgeManager judgeManager;

    // 调用YaoOJCodeSandbox-SDK，该SDK封装了签名生成、加密和连接到该代码沙箱服务
    // @Resource
    // private CodeSandbox codeSandbox;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {

        // 1.传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "习题不存在");
        }

        // 2.如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "提交习题正在判题中");
        }

        // 3.更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean updateResult = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);

        if (!updateResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "提交习题状态更新失败");
        }

        // 4.调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codeSandboxType);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取习题规定的输入用例
        String judgeCseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage(language);
        executeCodeRequest.setInputList(inputList);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        // 5.根据沙箱的执行结果，设置题目的判题状态和信息（设置执行判题的所需参数）并传入判题管理自动配置判题策略，执行判题并得到判题的结果
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = JudgeContext.builder()
                .judgeInfo(executeCodeResponse.getJudgeInfo())
                .inputList(inputList)
                .questionSubmit(questionSubmit)
                .judgeCaseList(judgeCaseList)
                .outputList(outputList)
                .question(question)
                .build();
        JudgeInfo judgeInfo = judgeManager.executeJudge(judgeContext);

        // 6.更新提交习题的数据
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        updateResult = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);

        if(!updateResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目状态更新失败");
        }

        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);

        return questionSubmitResult;
    }
}
