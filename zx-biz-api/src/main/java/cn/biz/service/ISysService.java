package cn.biz.service;

import cn.biz.dto.TableListDTO;
import cn.biz.dto.WeiboDTO;
import cn.biz.po.Weibo;
import cn.biz.vo.FundVO;
import cn.biz.vo.TableListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ISysService {
     void generateCode(String schema,String[] arr, HttpServletResponse response)throws Exception;
     IPage<TableListVO> getTableList(TableListDTO dto);
     List<String> getSchemas();
     /**微博查询**/
     IPage<Weibo> getWeiboSearchList(WeiboDTO dto);
     /**基金列表**/
     List<FundVO> fundList();
     /**更新基金列表**/
     Boolean updateAllFund();
}
