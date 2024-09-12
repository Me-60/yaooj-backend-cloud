package com.me.yaoojbackendjudgeservice.judge.codesandbox.impl;

import com.me.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.me.yaoojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现有的代码沙箱）
 */
public class ThirdPartySandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
