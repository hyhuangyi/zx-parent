package cn.common.util.json;

import cn.common.pojo.base.ResultDO;
import cn.common.pojo.servlet.BaseServletContextHolder;
import cn.common.pojo.servlet.ContextType;
import cn.common.util.string.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * filter中全局异常不会生效，需要通过流将异常或者结果写到前端,
 * 这里的request和response 配合了BaseServletContextHolder，
 * 能拿到request和response 前提BaseServletContextHolder在这之前set值了 prepare方法
 */
public class JsonResUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonResUtil.class);
    private static final String JSON_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonResUtil() {
    }
    static {
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        objectMapper.setDateFormat(dateFormat);
    }

    //方法一
    public static void renderJson(ResultDO obj) {
        HttpServletResponse response = BaseServletContextHolder.setResponseContext(ContextType.JSON_TYPE, new String[0]);
        HttpServletRequest request=BaseServletContextHolder.getRequest();
        if (StringUtils.isNotEmpty(obj.getCode())) {
            response.setStatus(Integer.valueOf(200));
        } else {
            response.setStatus(500);
        }
        renderJson(request,response, obj);
    }

    public static void renderJson(HttpServletRequest request,HttpServletResponse response, Object obj) {
        BaseServletContextHolder.setResponseContext(response, ContextType.JSON_TYPE, new String[0]);
        ServletOutputStream outputStream = null;
        try {
            setResponse(request,response);
            outputStream = response.getOutputStream();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);
            jsonGenerator.writeObject(obj);
        } catch (IOException var12) {
            logger.error("jackson解析对象错误!", var12);
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException var11) {
                logger.error("response输出流关闭错误!", var11);
            }

        }
    }

    //方法二
    public static void ajaxJsonResponse(ResultDO obj) throws IOException  {
        HttpServletResponse response = BaseServletContextHolder.setResponseContext(ContextType.JSON_TYPE, new String[0]);
        HttpServletRequest request=BaseServletContextHolder.getRequest();
        Writer writer = null;
        setResponse(request,response);
        try{
            writer = response.getWriter();
            String json = JSONObject.toJSONString(obj);
            writer.write(json);
        }finally{
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }

    private static void setResponse(HttpServletRequest request,HttpServletResponse response){
        String originHeader = request.getHeader("Origin");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setDateHeader("Expires", 0); // Proxies.
    }
}

