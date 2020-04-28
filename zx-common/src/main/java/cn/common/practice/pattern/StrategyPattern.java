package cn.common.practice.pattern;

/**
 * 策略模式
 * 模式结构
 * Context: 环境类
 * IStrategy: 抽象策略类
 * StrategyImpl: 具体策略类
 */
public class StrategyPattern {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubtract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }
}

//策略接口
 interface IStrategy {
    //算法
    public long operate(long a,long b);
}
//3个实现类
//加
class OperationAdd implements IStrategy{
    @Override
    public long operate(long a,long b) {
        return  a+b;
    }
}
//减
class OperationSubtract implements IStrategy{
    @Override
    public long operate(long a,long b) {
        return a-b;
    }
}
//乘
class OperationMultiply implements IStrategy{
    @Override
    public long operate(long a,long b) {
        return a*b;
    }
}
//上下文
class Context {
    private IStrategy strategy;
    //构造函数
    public Context(IStrategy strategy){
        this.strategy = strategy;
    }
    //具体实现
    public long executeStrategy(long a,  long b){
        return strategy.operate(a,b);
    }
}