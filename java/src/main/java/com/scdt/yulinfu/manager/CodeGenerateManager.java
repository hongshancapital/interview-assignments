package com.scdt.yulinfu.manager;

import com.scdt.yulinfu.dao.CodeGenerateDao;
import com.scdt.yulinfu.doamin.CodeGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Component
public class CodeGenerateManager {

    @Autowired
    private CodeGenerateDao codeGenerateDao;

    @Transactional(rollbackFor = Exception.class)
    public Long get() {
        Condition condition = new Condition(CodeGenerate.class);
        condition.and()
                .andEqualTo("id", 1L);
        List<CodeGenerate> generateList = codeGenerateDao.selectByCondition(condition);
        CodeGenerate generate;
        if (CollectionUtils.isEmpty(generateList)) {
            generate = new CodeGenerate();
            generate.setCurrent(-1L);
            generate.setId(1L);
            codeGenerateDao.insertSelective(generate);
        } else {
            generate = generateList.get(0);
        }
        long pos = generate.getCurrent();
        generate.setCurrent(pos + 1);
        codeGenerateDao.updateByConditionSelective(generate, condition);
        return pos;
    }

}
