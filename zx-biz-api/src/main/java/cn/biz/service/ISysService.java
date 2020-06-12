package cn.biz.service;

import cn.biz.dto.FundDTO;
import cn.biz.dto.TableListDTO;
import cn.biz.dto.WeiboDTO;
import cn.biz.po.Fund;
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
     /**处理csdn异步调用的方法**/
     void handleCsdn(String page,Integer minute);
     /**爬取微博**/
     void handleWeibo(String key);
     /**我的基金列表**/
     List<FundVO> fundList();
     /**更新基金列表**/
     Boolean updateAllFund();
     /**分页查询所有基金列表**/
     IPage<Fund> getAllFund(FundDTO dto);
     /**基金类型**/
     List<String>getFundType();
     /**获取费率为0的基金**/
     List<Fund> getZeroRateFund(int num)throws Exception;
     /**费率为0的基金当日排行**/
     List<FundVO> getZeroRateFundRank(int num) throws Exception;
}
