package cn.common.practice.pattern;

/**枚举的单例模式*/
public class SingletonPattern {
    private SingletonPattern(){}//私有构造
    public static SingletonPattern getInstance(){
        return SingletonEnum.INSTANCE.getInstance();
    }
    /*目前最为安全的实现单例的方法是通过内部静态enum的方法来实现，
     *因为JVM会保证enum不能被反射并且构造器方法只执行一次。*/
    private enum SingletonEnum{
        INSTANCE;

        private SingletonPattern singletonPattern;
        //JVM会保证此方法绝对只调用一次
        SingletonEnum(){
            singletonPattern =new SingletonPattern();
        }
        public SingletonPattern getInstance(){
            return singletonPattern;
        }
    }
}
