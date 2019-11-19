package cn.common.pojo.base;

import lombok.Data;
import java.io.Serializable;
/**
 * Created by zx
 **/
@Data
public class ResultDO<M>implements Serializable {
    private static final long serialVersionUID = -1369434131311843294L;
    private String code;
    private String msg;
    private String requestId;
    private M data;

    public ResultDO(){
    }

    public ResultDO(BaseErrorCode code) {
        this.code = code.getCode();
        this.msg = code.getDescription();
    }

    public ResultDO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDO(String code, String msg, M data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ResultDO( String requestId,String code, String msg, M data) {
        this.code = code;
        this.msg = msg;
        this.requestId = requestId;
        this.data = data;
    }
}
