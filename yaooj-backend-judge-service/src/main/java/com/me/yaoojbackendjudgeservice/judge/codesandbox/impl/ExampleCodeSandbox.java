package com.me.yaoojbackendjudgeservice.judge.codesandbox.impl;

import com.me.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.me.yaoojbackendmodel.model.codesandbox.JudgeInfo;
import com.me.yaoojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.me.yaoojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（测试业务流程沙箱）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        List<String> inputList = executeCodeRequest.getInputList();
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setMessage("===执行测试成功===");
        executeCodeResponse.setJudgeInfo(judgeInfo);
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        return executeCodeResponse;
    }
}
