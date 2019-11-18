package cn.biz.mapper;

import cn.biz.po.CdCity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 城市表 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface CdCityMapper extends BaseMapper<CdCity> {
    //放开注释加入redis缓存
    @Cacheable(value = "city",keyGenerator = "keyGenerator")
    List<CdCity> queryAllCity();

    CdCity getCity(String code);

    /*直接用注解写sql
     *cacheNames/value 指定缓存名字
     * key   缓存数据使用的key 默认使用方法参数的值
     *
     * */
    @Cacheable(value = "city",key ="'citys_'+#p0")
    @Select(" SELECT *FROM cd_city ORDER BY CODE ASC ")
    List<CdCity> query(Integer id);
}
