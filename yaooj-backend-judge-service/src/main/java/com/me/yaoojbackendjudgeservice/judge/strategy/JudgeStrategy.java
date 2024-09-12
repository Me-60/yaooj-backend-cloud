package com.me.yaoojbackendjudgeservice.judge.strategy;

import com.me.yaoojbackendjudgeservice.judge.strategy.model.JudgeContext;
import com.me.yaoojbackendmodel.model.codesandbox.JudgeInfo;

;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    JudgeInfo executeJudge(JudgeContext judgeContext);
}
