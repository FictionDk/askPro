package org.fictio.askPro.util;

import org.fictio.askPro.constans.ErrorConstans;
import org.fictio.askPro.pojo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@ControllerAdvice
public class GlobelExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(GlobelExceptionHandler.class);
	private static Gson gson = new Gson();
	
	@ExceptionHandler(value = TokenException.class)
	@ResponseBody
	public String tokenExceptionHandler(Exception e){
		log.error(e.getMessage());
		ResponseData<String> result = new ResponseData<>();
		result.setCode(ErrorConstans.TOKEN_ERROR_CODE);
		result.setMessage(ErrorConstans.TOKEN_ERROR_MSG);
		return gson.toJson(result);
	}
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public String defaultException(Exception e){
		log.info("1"+e.getMessage());
		log.info("2"+e.getLocalizedMessage());
		log.info(e.toString());
		ResponseData<String> result = new ResponseData<>();
		result.setMessage(e.getMessage());
		return gson.toJson(result);
	}

}
