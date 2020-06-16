package cn.biz.webMagic.pipline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

@Component
@Slf4j
public class CSDNPipeline extends FilePersistentBase implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        //拿到数据 可以存redis或者mysql
        log.info(resultItems.get("Title"));
    }
}
