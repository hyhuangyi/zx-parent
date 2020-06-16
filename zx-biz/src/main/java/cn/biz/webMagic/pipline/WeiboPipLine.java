package cn.biz.webMagic.pipline;

import cn.biz.mapper.WeiboMapper;
import cn.biz.po.Weibo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import java.util.List;

@Component
@Slf4j
public class WeiboPipLine extends FilePersistentBase implements Pipeline {
    @Autowired
    private WeiboMapper weiboMapper;
    @Override
    public void process(ResultItems resultItems, Task task) {
        /**拿到数据 可以存redis或者mysql**/
       List<Weibo> weiboList= resultItems.get("list");
       if(weiboList.size()!=0){
           weiboMapper.batchInsertOrUpdate(weiboList);//存mysql
       }
    }
}
