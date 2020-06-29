package cn.biz.service;

import cn.biz.dto.AddFundDTO;
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
     List<FundVO> fundList();
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
}
