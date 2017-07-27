package org.fictio.askPro.web;

import org.fictio.askPro.aop.annotation.UserAccess;
import org.fictio.askPro.pojo.Answer;
import org.fictio.askPro.pojo.AnswerReq;
import org.fictio.askPro.pojo.AnswerResp;
import org.fictio.askPro.pojo.Page;
import org.fictio.askPro.pojo.Question;
import org.fictio.askPro.pojo.RequestData;
import org.fictio.askPro.pojo.ResponseData;
import org.fictio.askPro.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/quest",method=RequestMethod.POST)
public class QuestionController {
	
	private static Logger log = LoggerFactory.getLogger(QuestionController.class);
	private static Gson gson = new Gson();
	@Autowired
	private QuestionService questionService;
	
	@ApiOperation(value="创建问题接口")
	@ApiImplicitParam(name="req",value="请求体({token:value,obj:{questionObj}})",required=true,dataType="RequestData<Question>")
	@RequestMapping("/create")
	@UserAccess
	public String createQuestion(@RequestBody RequestData<Question> req){
		ResponseData<String> result = new ResponseData<>();
		String userName = req.getUserName();
		if(req.getObj() != null){
			result = questionService.createQuestion(req.getObj(),userName);
		}else{
			result.setMessage("参数不能为空");
			log.info("req为空");
		}
		return gson.toJson(result);
	}
	
	@ApiOperation(value="更新问题接口")
	@ApiImplicitParams({@ApiImplicitParam(name="req",value="请求体外包",required=true,dataType="RequestData"),
				@ApiImplicitParam(name="obj",value="具体需要更新的数据",required=true,dataType="Question")})
	@RequestMapping("/update")
	@UserAccess
	public String updateQuestion(@RequestBody RequestData<Question> req){
		ResponseData<String> result = new ResponseData<>();
		if(req.getObj() != null){
			result = questionService.updateQuestion(req.getObj());
		}else{
			log.error(result.getMessage());
			result.setMessage("参数不能为空");
		}
		return gson.toJson(result);
	}
	
	@ApiOperation(value="获取用户问题列表接口")
	@ApiImplicitParam(name="req",value="需要token+起始页{'token':token,'obj':pageStart}",required=true,dataType="RequestData<Integer>")
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@UserAccess
	public String getQuestionListPage(@RequestBody RequestData<Integer> req){
		log.info(req.getUserName());
		ResponseData<Page<Question>> result = new ResponseData<>();
		result = questionService.getQuestionListByUser(req.getUserName(),req.getObj());
		return gson.toJson(result);
	}
	
	@ApiOperation(value="获取所有问题列表接口")
	@ApiImplicitParam(name="req",value="非必须,默认第一页",required=false,dataType="RequestData<Integer>")
	@RequestMapping(value="/allShow",method=RequestMethod.POST)
	public String allQuestionListShow(@RequestBody RequestData<Integer>req){
		ResponseData<Page<Question>> result = new ResponseData<>();
		String token = "";
		token = req.getToken();
		result = questionService.getQuestionShow(token,req.getObj());
		return gson.toJson(result);
	}
	
	@ApiOperation(value="获取问题下所有的回答列表")
	@ApiImplicitParam(name="req",value="需要token以及问题id",required=true,dataType="AnswerReq")
	@RequestMapping(value="/answerList",method=RequestMethod.POST)
	@UserAccess
	public String queryAnswerListByQuestId(@RequestBody RequestData<AnswerReq>req){
		ResponseData<Page<AnswerResp>> result = new ResponseData<>();
		result = questionService.getQuestionAnswerList(req.getUserName(),req.getObj());
		return gson.toJson(result);
	}
	
	@ApiOperation(value="给问题添加答案")
	@ApiImplicitParam(name="req",value="需要回答的所有对象结构+token",required=true,dataType="Answer")
	@RequestMapping(value="/answer",method=RequestMethod.POST)
	@UserAccess
	public String answerQuestion(@RequestBody RequestData<Answer>req){
		ResponseData<String> result = new ResponseData<>();
		Answer answer = req.getObj();
		answer.setUserName(req.getUserName());
		log.info(answer.toString());
		result = questionService.answerQuestion(answer);
		return gson.toJson(result);
	}
	
	@ApiOperation(value="获取指定问题详情")
	@ApiImplicitParam(name="req",value="需要token+问题id",required=true,dataType="RequestData<T>")
	@RequestMapping("/get")
	@UserAccess
	public String getQuestionById(@RequestBody RequestData<Integer> req){
		ResponseData<Question> result = new ResponseData<>();
		result = questionService.getQuestionById(req.getObj(),req.getUserName());
		return gson.toJson(result);
	}
	
	@ApiOperation(value="点/取消赞接口")
	@ApiImplicitParam(name="req",value="需要token+answerId",required=true,dataType="RequestData<T>")
	@RequestMapping(value="/commend",method=RequestMethod.POST)
	@UserAccess
	public String commend(@RequestBody RequestData<Integer> req){
		ResponseData<String> result = new ResponseData<>();
		result = questionService.commendAnswer(req.getUserName(),req.getObj());
		return gson.toJson(result);
	}

}
