package cn.webapp.configuration.bean;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import java.util.List;

/**
 * 添加Sql注入方法,支持空字段更新
 */
public class ZxSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(){
        List<AbstractMethod> methodList=super.getMethodList();
        methodList.add(new BatchInsertMethod());
        methodList.add(new TruncateMethod());
        return methodList;
    }
}
