package xiejin.java.interview.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiejin
 * @date 2020/7/12 11:01
 */
@Component
@Slf4j
public class UpdateFieldsMetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(">>>>>>>插入时填充策略启动");
        String operator = "admin";
        this.strictInsertFill(metaObject, "creator", String.class, operator);
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updator", String.class, operator);
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        /*this.setFieldValByName("creator", operator, metaObject);
        this.setFieldValByName("createTime", operator, metaObject);
        this.setFieldValByName("creator", operator, metaObject);
        this.setFieldValByName("updateTime", operator, metaObject);*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info(">>>>>>>更新时填充策略启动");
        String operator = "admin";
        if (StringUtils.isEmpty(operator)) {
            operator = "admin";
        }
        this.strictInsertFill(metaObject, "updator", String.class, operator);
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }
}
