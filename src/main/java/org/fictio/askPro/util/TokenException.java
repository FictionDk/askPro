package org.fictio.askPro.util;

import org.fictio.askPro.constans.ErrorConstans;

public class TokenException extends RuntimeException {
	private static final long serialVersionUID = -7605711680078207365L;
	public TokenException(){
	    super(ErrorConstans.TOKEN_ERROR_MSG);
	}
}
