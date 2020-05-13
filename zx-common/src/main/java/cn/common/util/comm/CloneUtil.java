package cn.common.util.comm;

import cn.common.pojo.base.Token;
import java.io.*;

/**
 * 什么时候使用序列化
 * 当对象保存到物理介质的时候，比如对象保存到磁盘、文件
 * 当对象在网络上传输的时候，比如通过套接字传输对象
 * 当对象远程过程调用的时候，比如通过RMI调用对象
 */
public class CloneUtil {

    private CloneUtil() {
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();
        // 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
    }

    public static void main(String[] args)throws Exception {
        Token token=new Token();
        token.setUsername("123");
        token.setRoles(new String[]{"2","1"});
        Token token1=clone(token);
        token1.setRoles(new String[]{"2222","1111"});
        System.out.println(token);
        System.out.println(token1);
    }
}