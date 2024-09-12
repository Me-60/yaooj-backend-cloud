package com.me.yaoojbackendjudgeservice.judge.codesandbox;

import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
