package cn.common.exception;

import java.io.Serializable;

/**
 * Created by huangYi on 2018/11/1.
 **/
public class ZxException extends RuntimeException implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ZxException() {
    }

    public ZxException(String message) {
        super(message);
    }
    

    public ZxException(String message, Throwable cause) {
        super(message, cause);
    }
}
