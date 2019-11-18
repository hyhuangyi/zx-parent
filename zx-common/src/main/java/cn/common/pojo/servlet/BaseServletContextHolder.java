package cn.common.pojo.servlet;

import cn.common.pojo.base.Token;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServletContextHolder {
    private static final ThreadLocal<ServletData> manager = new ThreadLocal();

    public BaseServletContextHolder() {
    }

    public static  void prepare(Token data, HttpServletRequest request, HttpServletResponse response) {
        ServletData sd = new ServletData();
        sd.setRequest(request);
        sd.setResponse(response);
        sd.setData(data);
        manager.set(sd);
    }

    public static HttpServletRequest getRequest() {
        return (manager.get()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return (manager.get()).getResponse();
    }

    public static Token getToken() {
        return (manager.get()).getData();
    }

    public static void setToken(Token token) {
        ServletData servletData = manager.get();
        servletData.setData(token);
        manager.set(servletData);
    }

    public static void clear() {
        manager.remove();
    }

    public static HttpServletResponse setResponseContext(ContextType contentType, String... header) {
        HttpServletResponse response = getResponse();
        return setResponseContext(response, contentType, header);
    }

    public static HttpServletResponse setResponseContext(HttpServletResponse response, ContextType contentType, String... header) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", contentType.getValue() + ";charset=UTF-8");
        return response;
    }
}
