package cn.biz.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class ZxListenerEvent extends ApplicationEvent {
    private String name;
    private Integer age;
    public ZxListenerEvent(Object source) {
        super(source);
    }
    public ZxListenerEvent(Object source, String name, Integer age) {
        super(source);
        this.name = name;
        this.age = age;
    }
}
