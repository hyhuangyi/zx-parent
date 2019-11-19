package cn.common.pojo.servlet;

import cn.common.pojo.base.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServletData {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Token data;
}
