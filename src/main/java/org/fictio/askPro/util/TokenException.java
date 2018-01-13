package org.fictio.askPro.util;

import org.fictio.askPro.constans.ErrorConstans;

/**
 * 自定义token检查失败异常
 * 使用检查型异常方便全局异常捕捉类捕捉
 * @author dk
 */
public class TokenException extends RuntimeException {
	private static final long serialVersionUID = -7605711680078207365L;
	public TokenException(){
	    super(ErrorConstans.TOKEN_ERROR_MSG);
	}
}
