package com.example.clone.util;

import com.alibaba.fastjson.JSON;
import com.example.clone.beans.Room;
import com.example.clone.beans.Tenant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CloneUtil {

    private CloneUtil() {
        throw new AssertionError();
    }


    @SuppressWarnings("unchecked")
    private static <T extends Serializable> T deepClone(T obj) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();

        //说明：调用ByteArrayInputStream或ByteArrayOutputStream的close方法没有任何意义
        //这两个基于内存的流，只要垃圾回收器清理对象，就能够释放资源
    }

    public static void main(String[] args) throws Exception {

        List<Tenant> tenants = new ArrayList<>();
        tenants.add(new Tenant("john", "15189609457"));
        tenants.add(new Tenant("joe", "17625911413"));
        Room roomA = new Room(123, tenants);
        System.out.println(JSON.toJSONString(roomA));
        Room roomB = roomA;
        roomB.setRoomNo(456);
        System.out.println(JSON.toJSONString(roomA));
        /*
        运行结果：
        {"roomNo":123,"tenants":[{"name":"john","tel":"15189609457"},{"name":"joe","tel":"17625911413"}]}
        {"roomNo":456,"tenants":[{"name":"john","tel":"15189609457"},{"name":"joe","tel":"17625911413"}]}

        为什么改变roomB的房间编号，roomA的编号也发生了变化呢？
        原因出在Room roomB = roomA; 这一句。该语句的作用是
        将roomA的引用赋值给roomB，
        这样，roomA和roomB指向内存堆中同一个对象
         */

        roomB = CloneUtil.deepClone(roomA);
        roomB.setRoomNo(789);
        System.out.println(JSON.toJSONString(roomA));
        /*
        深度复制以后，更改roomB的属性将不会在影响到roomA.
        运行结果：
        {"roomNo":123,"tenants":[{"name":"john","tel":"15189609457"},{"name":"joe","tel":"17625911413"}]}
        {"roomNo":456,"tenants":[{"name":"john","tel":"15189609457"},{"name":"joe","tel":"17625911413"}]}
        {"roomNo":456,"tenants":[{"name":"john","tel":"15189609457"},{"name":"joe","tel":"17625911413"}]}
         */


    }

    /*
     实现对象克隆有两种方式：

      1). 实现Cloneable接口并重写Object类中的clone()方法；

      2). 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆。

      注意：基于序列化和反序列化实现的克隆不仅仅是深度克隆，更重要的
      是通过泛型限定，可以检查出要克隆的对象是否支持序列化，这项检查是
      编译器完成的，不是在运行时抛出异常，
      这种是方案明显优于使用Object类的clone方法克隆对象。
      让问题在编译的时候暴露出来总是优于把问题留到运行时。

      注： 集合的clone，ArrayList 默认实现了Cloneable，
      但是List<A> A对象不是深度克隆，A对象的内容也是使用同一个内存地址，
      所以A对象也必须实现Cloneable
     */

}
