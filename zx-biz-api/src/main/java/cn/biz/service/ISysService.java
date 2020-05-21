package cn.biz.service;

import cn.biz.dto.TableListDTO;
import cn.biz.vo.TableListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ISysService {
     void generateCode(String schema,String[] arr, HttpServletResponse response)throws Exception;
     IPage<TableListVO> getTableList(TableListDTO dto);
     List<String> getSchemas();
}
