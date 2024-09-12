package com.me.yaoojbackendjudgeservice.judge.strategy.manager;

import com.me.yaoojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.me.yaoojbackendjudgeservice.judge.strategy.impl.DefaultJudgeStrategyImpl;
import com.me.yaoojbackendjudgeservice.judge.strategy.impl.JavaJudgeStrategyImpl;
import com.me.yaoojbackendjudgeservice.judge.strategy.model.JudgeContext;
import com.me.yaoojbackendmodel.model.codesandbox.JudgeInfo;
import com.me.yaoojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     * 通过语言来确定判题策略
     * 为策略模式，有点像简单工厂模式
     * @param judgeContext
     * @return
     */
    public JudgeInfo executeJudge(JudgeContext judgeContext) {

        System.out.println("====" + judgeContext + "====");

        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();

        String language = questionSubmit.getLanguage();

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();

        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategyImpl();
        }

        return judgeStrategy.executeJudge(judgeContext);
    }
}
