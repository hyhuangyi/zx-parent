package cn.webapp.advice;

import cn.common.exception.ZxException;
import cn.common.util.encrypt.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Author:zx 解决流只能读一次问题
 * DateTime:2019/4/9
 **/
@Slf4j
public class ZxHttpInputMessage implements HttpInputMessage {

    private HttpHeaders headers;
    private InputStream body;

    /**
     * 处理流
     *
     * @param inputMessage
     * @param privateKey   私钥
     * @throws Exception
     */
    public ZxHttpInputMessage(HttpInputMessage inputMessage, String privateKey) throws Exception {
        //header
        this.headers = inputMessage.getHeaders();
        //读流获取body
        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        if (StringUtils.isNotEmpty(content)) {
            if (StringUtils.isEmpty(privateKey)) {
                log.error("privateKey is null");
                throw new ZxException("privateKey is null");
            }
            String res  = RSAUtils.decryptByPrivateKey(content, privateKey);
            this.body = new ByteArrayInputStream(res.getBytes());
        }else {
            this.body = new ByteArrayInputStream(content.getBytes());
        }
    }

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
