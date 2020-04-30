package cn.common.practice.pattern;

import lombok.Data;

public class PrototypePattern{
    public static void main(String[] args) throws Exception{
        Prototype prototype=new Prototype("zx",25);
        Prototype clone=(Prototype) prototype.clone();
        clone.setAge(28);
        System.out.println(prototype);
        System.out.println(clone);
    }
}
//具体原型类
@Data
class Prototype implements Cloneable {
    private String name;
    private Integer age;
    Prototype() {}
    public Prototype(String name, Integer age) {
        this.name = name;
        this.age = age;
        System.out.println("具体原型创建成功！");
    }

    public Object clone() throws CloneNotSupportedException{
        System.out.println("具体原型复制成功！");
        return (Prototype)super.clone();
    }
}
