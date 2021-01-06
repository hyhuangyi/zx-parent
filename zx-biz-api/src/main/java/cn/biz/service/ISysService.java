package cn.biz.service;

import cn.biz.dto.*;
import cn.biz.po.Fund;
import cn.biz.po.Weibo;
import cn.biz.po.XqData;
import cn.biz.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ISysService {
     /*生成代码*/
     void generateCode(String schema,String[] arr, HttpServletResponse response)throws Exception;
     /*表列表*/
     IPage<TableListVO> getTableList(TableListDTO dto);
     /*获取schemas*/
     List<String> getSchemas();
     /*微博查询*/
     IPage<Weibo> getWeiboSearchList(WeiboDTO dto);
     /*处理csdn异步调用的方法*/
     void handleCsdn(String page,Integer minute);
     /*爬取微博*/
     void handleWeibo(String key);
     /*清空微博*/
     Boolean cleanWeibo();
     /*我的基金列表*/
     List<FundVO> fundList(long userId);
     /*修改持有金额*/
     Boolean updateHoldMoney(Long id,String holdMoney);
     /*修改备注*/
     Boolean updateRemark(Long id,String remark);
     /*更新基金列表*/
     Boolean updateAllFund();
     /*分页查询所有基金列表*/
     IPage<Fund> getAllFund(FundDTO dto);
     /*基金下拉选*/
     List<String>getFundSelect();
     /*新增基金*/
     boolean addFund(AddFundDTO dto);
     /*删除基金*/
     boolean delFund(Long id);
     /*基金类型*/
     List<String>getFundType();
     /*获取费率为0的基金*/
     List<Fund> getZeroRateFund(int num)throws Exception;
     /*费率为0的基金当日排行*/
     List<FundVO> getZeroRateFundRank(int num) throws Exception;
     /*获取大盘信息*/
     StockVO getStockInfo();
     /*获取大盘chart数据*/
     Map getStockChartData(String type);
     /*获取年内涨幅少的股票*/
     List<XueqiuVO.DataBean.ListBean> getXueqiuList(double percent,int yearPercent);
     /*获取雪球历史数据*/
     IPage<XqData> getXqHistoryList(XqHistoryDTO dto);
}
