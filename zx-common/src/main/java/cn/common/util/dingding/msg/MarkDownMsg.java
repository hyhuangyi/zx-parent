package cn.common.util.dingding.msg;

import lombok.Data;

@Data
public class MarkDownMsg {

	private String msgtype;
	
	private MarkDownContent markdown;
	
	private At at;
	
	private Boolean isAtAll;
	
}
