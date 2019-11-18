package cn.common.pojo.servlet;

import cn.common.pojo.base.Token;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletData {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Token data;

    public ServletData() {
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Token getData() {
        return this.data;
    }

    public void setData(Token data) {
        this.data = data;
    }

}
