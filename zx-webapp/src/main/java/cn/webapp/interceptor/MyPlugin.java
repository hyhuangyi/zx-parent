package cn.webapp.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import java.sql.Statement;
import java.util.Properties;

/**
 *  自定义mybatis插件
 * 拦截所以数据库sql执行时长超过x毫秒，并记录sql
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
public class MyPlugin implements Interceptor {

    private static long time;

    //方法拦截
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //通过StatementHandler获取执行的sql
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        }finally {
            long end = System.currentTimeMillis();
            long sqlCost=end-start;
            log.info("sqlCost:"+sqlCost);
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            if (sqlCost >=time) {
               log.info("本次数据库操作是慢查询，sql是:" + sql+",执行时间："+sqlCost+"ms");
            }
        }
    }

    //获取到拦截的对象，底层也是通过代理实现的，实际上是拿到一个目标代理对象
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    //获取设置的阈值等参数
    @Override
    public void setProperties(Properties properties) {
        this.time = Long.parseLong(properties.getProperty("time"));
    }
}

