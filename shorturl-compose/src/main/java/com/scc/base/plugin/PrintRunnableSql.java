package com.scc.base.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.List;
import java.util.Properties;


/**
 * print running sql to make debug easily
 *
 * @author renyunyi
 * @date 2022/4/24 2:51 PM
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
                ),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
                ),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
@Slf4j
public class PrintRunnableSql implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String sql = null;
        Object o = null;
        try {
            sql = this.runnableSQL(invocation);
            log.info("print sql -> {}" + sql);
            o = invocation.proceed();
        } catch (Exception e) {
            log.error("Invocation is: {}, SQL is: {}, error is: {}", invocation, sql, e.getMessage());
        }
        return o;
    }

    public String runnableSQL(Invocation invocation) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        BoundSql boundSql = ((MappedStatement) invocation.getArgs()[0]).getBoundSql(invocation.getArgs()[1]);
        String replacedSql = boundSql.getSql().replace("\n", "");
        Object parameterObject = invocation.getArgs()[1];
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }

                    String paramString = value == null ? "null" : value.toString();
                    replacedSql = replacedSql.replaceFirst("\\?", "'" + paramString + "'");

                }
            }
        }
        return replacedSql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}