package cn.common.practice.serializable;

import lombok.Data;
import java.io.*;

/**
 * 什么时候使用序列化
 * 当对象保存到物理介质的时候，比如对象保存到磁盘、文件
 * 当对象在网络上传输的时候，比如通过套接字传输对象
 * 当对象远程过程调用的时候，比如通过RMI调用对象
 */
public class SerializableDemo {
    private static String file="E:\\zx";
    static {
        File f=new File(file);
        if(!f.exists()){
            f.mkdirs();
        }
    }

    /**
     * 序列化
     */
    private static void writeSlb(){
        ObjectOutputStream objectOutputStream=null;
        try {
            Slb slb=new Slb();
            slb.setAge(20);
            slb.setName("子轩");
            objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(file+File.separator+"zx.txt")));
            objectOutputStream.writeObject(slb);
            System.out.println("序列化成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objectOutputStream!=null){
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化
     * @return
     */
    public static Slb readSlb(){
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream=new ObjectInputStream(new FileInputStream(new File(file+File.separator+"zx.txt")));
           Slb slb=(Slb)objectInputStream.readObject();
           System.out.println("反序列化成功"+slb);
           return slb;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        writeSlb();
        readSlb();
    }
}
@Data
class Slb implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer age;
}