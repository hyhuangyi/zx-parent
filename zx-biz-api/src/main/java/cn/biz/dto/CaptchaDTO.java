package cn.biz.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
@Data
public class CaptchaDTO implements Serializable {
    @ApiModelProperty(value = "宽度")
    private Integer width;
    @ApiModelProperty(value = "高度")
    private Integer height;
}
