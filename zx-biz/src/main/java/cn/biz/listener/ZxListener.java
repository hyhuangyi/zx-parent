package cn.biz.listener;

import cn.biz.event.ZxListenerEvent;
import cn.common.exception.ZxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ZxListener {
    /**
     * TransactionalEventListener  只有当前事务提交之后，才会执行事件监听器的方法（有事务情况下）
     * 异步方法里出现异常不会对触发方法事务有影响的
     * 如果不加异步情况下 （不使用@Async） 那么使用 @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)对事务没影响  使用@EventListener事务会回滚
     */
    @Async("myTaskAsyncPool")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void doTest(ZxListenerEvent listener){
        log.info(Thread.currentThread().getName()+"name="+listener.getName() +";age="+listener.getAge());
        if(1==1){
            throw new ZxException("假装异常");
        }
    }
}
