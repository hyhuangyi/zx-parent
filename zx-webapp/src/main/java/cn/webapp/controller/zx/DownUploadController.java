package cn.webapp.controller.zx;

import cn.common.exception.ZXException;
import cn.common.pojo.base.ResultDO;
import cn.common.util.oss.AliOssUtil;
import cn.webapp.aop.annotation.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huangYi on 2018/8/12
 **/
@Api(description = "下载文件相关")
@RequestMapping("/comm")
@RestController
@Slf4j
public class DownUploadController {

    @RequestMapping(value = "/down", method = RequestMethod.GET)
    @ApiOperation("下载文件")
    @OperateLog(operation = "下载")
    public void download(String path, HttpServletRequest request, HttpServletResponse response) {

        FileInputStream fis = null;
        OutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File file = new File(path);
        if (file.exists()) {

            try {
                fis = new FileInputStream(file); //输入流
                fos = response.getOutputStream(); // 获取字节流
                bis = new BufferedInputStream(fis);//输入缓冲流
                bos = new BufferedOutputStream(fos);//输出缓冲流
                //解决中文乱码  //http header头要求其内容必须为iso8859-1编码  //utf8改成gbk兼容ie
                String name = new String(file.getName().getBytes("gbk"), "iso8859-1");
                //方法二
                String name1 = URLEncoder.encode(file.getName(), "utf8");

                response.setContentType("application/force-download");//设置返回的文件类型,强制下载不打开
                response.addHeader("Content-Length", "" + file.length());//文件大小
                response.addHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + name);// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                // 开始向网络传输文件流
                while ((bytesRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                bis.close();
                bos.flush();// 清空输出缓冲流
                bos.close();
                fos.close();

            } catch (IOException e) {
                log.error("下载出错", e);
            }
        }
    }

    @ApiOperation("上传")
    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file, RedirectAttributes result) {
        if (file.isEmpty()) {
            throw new ZXException("文件不能为空");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String path = "D:" + File.separator + format.format(new Date());
        String filename = file.getOriginalFilename();
        log.info("filename=" + filename);
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        File targetFile = new File(path, filename);
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存异常", e);
        }
    }

    @ApiOperation("OSS上传文件")
    @PostMapping("/oss/upload")
    public ResultDO upload(@ApiParam(value = "文件", required = true) @RequestParam MultipartFile multipartFile) {
        String str = "";
        if (multipartFile.isEmpty()) {
            throw new ZXException("文件不能为空");
        }
        str = AliOssUtil.fileUpload(multipartFile, "banner");
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(0, str.indexOf("?"));
        }
        return new ResultDO("200", "成功", str);
    }

    @ApiOperation("OSS文件下载")
    @GetMapping("/oss/down")
    public void ossDownload(String objKey, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream bos = AliOssUtil.ossDownLoad(objKey);
        OutputStream outputStream = response.getOutputStream();
        try {
            response.setContentType("application/force-download");//设置返回的文件类型,强制下载不打开
            response.addHeader("Content-Length", "");//文件大小
            response.addHeader(
                    "Content-disposition",
                    "attachment;filename="
                            + URLEncoder.encode("测试" + objKey.substring(objKey.lastIndexOf(".")), "utf8"));// 设置头部信息
            if (bos != null) {
                outputStream.write(bos.toByteArray());
            }
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }

    @ApiOperation("读取jar下文件测试")
    @RequestMapping(value = "/readResource",method = RequestMethod.GET)
    public void readResource(HttpServletResponse response) {
        //文件流
        InputStream is = getClass().getResourceAsStream("/generatorConfig.xml");
        try {
            response.setContentType("application/force-download");//设置返回的文件类型,强制下载不打开
            response.addHeader("Content-Length", "" + is.available());//文件大小
            response.addHeader(
                    "Content-disposition",
                    "attachment;filename="
                            + URLEncoder.encode("读取jar下文件测试.xml","utf8"));// 设置头部信息

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw  new ZXException("下载异常");
        } finally {
            try {
                if(is!=null){
                    is.close();
                }
                response.getOutputStream().close();
            }catch (Exception e){
                throw new ZXException("流关闭异常");
            }
        }
    }
}
