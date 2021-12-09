package cn.common.util.comm;

import cn.common.pojo.base.ResultDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * 过滤返回字段
 */
@Slf4j
public class RestFieldFilterUtil {
    //header
    private static String INCLUDE_FIELDS = "x-include-fields";
    private static String EXCLUDE_FIELDS = "x-exclude-fields";
    //参数
    private static String P_INCLUDE_FIELDS = "x-include-fields";
    private static String P_EXCLUDE_FIELDS = "x-exclude-fields";

    /**
     * 返回前端需要的字段
     *
     * @param o
     * @param request includeParam  excludeParam
     * @return
     */
    public static Object handReturnValue(Object o, ServerHttpRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();
        String includeParam = httpServletRequest.getHeader(INCLUDE_FIELDS);
        if (StringUtils.isEmpty(includeParam)) includeParam = httpServletRequest.getParameter(P_INCLUDE_FIELDS);
        String excludeParam = httpServletRequest.getHeader(EXCLUDE_FIELDS);
        if (StringUtils.isEmpty(excludeParam)) excludeParam = httpServletRequest.getParameter(P_EXCLUDE_FIELDS);

        String include = StringUtils.trimToEmpty(includeParam);
        String exclude = StringUtils.trimToEmpty(excludeParam);
        try {
            if (include.length() > 0 || exclude.length() > 0) {
                Set<String> includes = include.length() == 0 ? new HashSet<>() : new HashSet<>(Arrays.asList(include.split(",")));
                Set<String> excludes = exclude.length() == 0 ? new HashSet<>() : new HashSet<>(Arrays.asList(exclude.split(",")));
                if (o instanceof ResultDO) {
                    ResultDO result = (ResultDO) o;
                    Object object = result.getData();
                    result.setData(convertResult(object, includes, excludes));
                    return request;
                } else if (o instanceof IPage) {
                    IPage result = (IPage) o;
                    List datas = result.getRecords();
                    result.setRecords((List) convertResult(datas, includes, excludes));
                    return result;
                } else if (o instanceof List) {
                    return convertCollection((List) o, includes, excludes);
                } else {
                    return convertObject(o, includes, excludes);
                }
            }
        } catch (Exception e) {
            log.error("handReturnValue 转化异常", e);
        }
        return o;
    }

    /**
     * convert objects to maps
     *
     * @param object
     * @param includes
     * @param excludes
     * @return
     * @throws Exception
     */
    private static Object convertResult(Object object, Set<String> includes, Set<String> excludes) throws Exception {
        if (object instanceof Object[]) {
            Object[] objects = (Object[]) object;
            return convertArray(objects, includes, excludes);
        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            return convertCollection(collection, includes, excludes);
        } else {
            return convertObject(object, includes, excludes);
        }
    }

    /**
     * 转化collection
     *
     * @param collection
     * @param includes
     * @param excludes
     * @return
     * @throws Exception
     */
    private static Collection<Map> convertCollection(Collection collection, Set<String> includes, Set<String> excludes) throws Exception {
        Collection<Map> result = new ArrayList<>();
        for (Object item : collection) {
            result.add(convertObject(item, includes, excludes));
        }
        return result;
    }

    /**
     * 转化数组
     *
     * @param objects
     * @param includes
     * @param excludes
     * @return
     * @throws Exception
     */
    private static Map[] convertArray(Object[] objects, Set<String> includes, Set<String> excludes) throws Exception {
        Map[] result = new HashMap[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i] = convertObject(objects[i], includes, excludes);
        }
        return result;
    }

    /**
     * convert object to map
     *
     * @param object   input
     * @param includes include props
     * @param excludes exclude props
     * @return
     * @throws Exception
     */
    private static Map convertObject(Object object, Set<String> includes, Set<String> excludes) throws Exception {
        Map<Object, Object> result = new HashMap<>();
        if (!(object instanceof Map)) {
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(object);
            for (PropertyDescriptor pd : pds) {
                String name = pd.getName();
                if (name.equals("class")) {
                    continue;
                }
                if (!excludes.isEmpty() && excludes.contains(name)) {
                    continue;
                }
                if (!includes.isEmpty() && !includes.contains(name)) {
                    continue;
                }
                result.put(name, PropertyUtils.getProperty(object, name));
            }
        } else {
            Map<Object, Object> map = (Map<Object, Object>) object;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                String name = entry.getKey() == null ? "" : entry.getKey().toString();
                if (!excludes.isEmpty() && excludes.contains(name)) {
                    continue;
                }
                if (!includes.isEmpty() && !includes.contains(name)) {
                    continue;
                }
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
