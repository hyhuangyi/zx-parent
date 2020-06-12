package cn.common.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by huangy
 * 配置文件类
 */
@Component
public class PropertyPlaceUtil {

    @Value("${file.export.path}")
    private String fileExportPath;//文件下载目录
    @Value("${s.weibo.com.cookie}")
    private String cookie;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getFileExportPath() {
        return fileExportPath;
    }

    public void setFileExportPath(String fileExportPath) {
        this.fileExportPath = fileExportPath;
    }
}
