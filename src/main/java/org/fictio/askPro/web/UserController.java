package org.fictio.askPro.web;

import javax.validation.Valid;

import org.fictio.askPro.aop.annotation.UserAccess;
import org.fictio.askPro.constans.ErrorConstans;
import org.fictio.askPro.pojo.RequestData;
import org.fictio.askPro.pojo.ResponseData;
import org.fictio.askPro.pojo.User;
import org.fictio.askPro.pojo.UserIndex;
import org.fictio.askPro.pojo.UserLogin;
import org.fictio.askPro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	private static Gson gson = new Gson();
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户退出登录接口")
	@ApiImplicitParam(name="reqData",value="请求体({token:value})",required=true,dataType="RequestData")
	@RequestMapping(value="/loginOut",method=RequestMethod.POST)
	@UserAccess
	public String loginOut(@RequestBody RequestData<String> reqData){
		ResponseData<String> result = new ResponseData<>();
		result = userService.userLoginOut(reqData.getToken(),reqData.getUserName());
		return gson.toJson(result);
	}
	
	@ApiOperation(value="用户登录接口",notes="")
	@ApiImplicitParam(name="u",value="用户登录实体",required=true,dataType="UserLogin")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String userLogin(@Valid @RequestBody UserLogin u){
		ResponseData<String> result = new ResponseData<>();
		result = userService.userLogin(u);
		return gson.toJson(result);
	}
	
	@ApiOperation(value="获取首页个人资料接口")
	@ApiImplicitParam(name="requestData",value="请求体",required=true,dataType="RequestData")
	@RequestMapping(value="/userInfoGet",method=RequestMethod.POST)
	@UserAccess
	public String userInfoGet(@RequestBody RequestData<String> requestData){
		ResponseData<UserIndex> result = new ResponseData<>();
		result = userService.getUserIndexInfo(requestData.getToken());
		log.info("getUserInfo={}",result.toString());
		return gson.toJson(result);
	}
	
	@ApiOperation(value="请求发送注册码接口")
	@ApiImplicitParam(name="u",value="用户实体",required=true,dataType="User")
	@ApiResponse(code=1,message="success",responseContainer="token")
	@RequestMapping(value="/getRegisteCode",method=RequestMethod.POST)
	public String getRegisteCode(@RequestBody User u){
		ResponseData<String> result = new ResponseData<>();
		if(userService.registeCode(u)){
			result.setSuccess();
		}else{
			result.setMessage(ErrorConstans.REGISTE_CODE_ERROR_MSG);
			result.setCode(ErrorConstans.REGISTE_CODE_ERROR_CODE);
		}
		return gson.toJson(result);
	}
	
	@ApiOperation(value="用户注册接口")
	@ApiImplicitParam(name="req",value="请求体",required=true,dataType="RequestData<User>")
	@RequestMapping(value="/registeAct",method=RequestMethod.POST)
	public String registeAct(@RequestBody RequestData<User> req){
		ResponseData<String> result = new ResponseData<>();
		result = userService.registeByMobile(req.getObj(), req.getCode());
		log.info(result.toString());
		return gson.toJson(result);
	}
	
	@RequestMapping(value="/jsonTest",method=RequestMethod.POST)
	public String jsonTest(@Valid @RequestBody User u){
		ResponseData<String> result = new ResponseData<>();
		log.info(u.toString());
		result.setSuccess();
		return gson.toJson(result);
	}
	
}
