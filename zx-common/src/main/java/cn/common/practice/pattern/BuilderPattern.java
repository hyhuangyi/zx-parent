package cn.common.practice.pattern;

import lombok.Data;

/**
 * 创建者模式
 */
public class BuilderPattern {
    public static void main(String[] args) {
        Builder builder=new MacBoolBuilder();
        Director director=new Director(builder);
        director.builder("mac主板","mac显示频","macOs","A13Cpu");
        System.out.println(builder.getComputer().toString());
        /*------------------------------------------------------------*/

    }
}

//产品类
@Data
class Computer{
    private String board;//主板
    private String display;//显示器
    private String system;//系统
    private String cpu;//cpu
}
//抽象builder类
abstract class Builder{
    //设置主板
    public abstract void builderBoard(String board);
    //设置显示器
    public abstract void builderDisplay(String display);
    //设置系统
    public abstract void builderSystem(String system);
    //设置cpu
    public abstract void builderCpu(String cpu);
    //创建computer
    public abstract Computer getComputer();
}
//具体builder类
class MacBoolBuilder extends Builder{
    private Computer computer=new Computer();
    @Override
    public void builderBoard(String board) {
        computer.setBoard(board);
    }

    @Override
    public void builderDisplay(String display) {
        computer.setDisplay(display);
    }

    @Override
    public void builderSystem(String system) {
        computer.setSystem(system);
    }

    @Override
    public void builderCpu(String cpu) {
        computer.setCpu(cpu);
    }

    @Override
    public Computer getComputer() {
        return computer;
    }
}
//Director类的主要作用是调用具体的builder，来构建对象的各个部分，Director类起到封装作用，避免高层模块深入到建造者内部的实现类。
class Director {

    Builder builder=null;

    public Director(Builder builder){
        this.builder=builder;
    }

    public void builder(String board,String display,String system,String os){
        builder.builderBoard(board);
        builder.builderDisplay(display);
        builder.builderSystem(system);
        builder.builderCpu(os);
    }
}
@Data
class ThinkPad {
    private String board;//主板
    private String display;//显示器
    private String system;//系统
    private String cpu;//cpu

    private ThinkPad(Builder builder){
        this.cpu=builder.cpu;
        this.display=builder.display;
        this.board=builder.board;
        this.system=builder.system;
    }

    static class Builder{
        private String board;//主板
        private String display;//显示器
        private String system;//系统
        private String cpu;//cpu
        public Builder setBoard(String board){
            this.board=board;
            return this;
        }
        public Builder setDisplay(String display){
            this.display=display;
            return this;
        }
        public Builder setSystem(String system){
            this.system=system;
            return this;
        }
        public Builder setCpu(String cpu){
            this.cpu=cpu;
            return this;
        }
        public ThinkPad builder(){
            return new ThinkPad(this);
        }
    }

    public static void main(String[] args) {
        ThinkPad thinkPad=new ThinkPad.Builder().setBoard("Intel").setDisplay("华为").setCpu("I7").setSystem("win10").builder();
        System.out.println(thinkPad.toString());
    }
}