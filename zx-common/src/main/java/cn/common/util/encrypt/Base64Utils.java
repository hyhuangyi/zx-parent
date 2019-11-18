package cn.common.util.encrypt;

import org.apache.commons.codec.binary.Base64;

/**
 * 用途描述
 *
 * @author 朱思雷
 * @version $Id: Base64Utils, v0.1
 * @company 杭州信牛网络科技有限公司
 * @date 2019年01月01日 下午9:50 朱思雷 Exp $
 */
public class Base64Utils {

    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decodeBase64(base64.getBytes("UTF-8"));
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encodeBase64(bytes));
    }
}
