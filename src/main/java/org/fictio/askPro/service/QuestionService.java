package org.fictio.askPro.service;

import java.util.Date;
import java.util.List;

import org.fictio.askPro.constans.ErrorConstans;
import org.fictio.askPro.constans.ScoreTypeConstans;
import org.fictio.askPro.dao.QuestionMapper;
import org.fictio.askPro.pojo.Question;
import org.fictio.askPro.pojo.ResponseData;
import org.fictio.askPro.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class QuestionService {
	private static Logger log = LoggerFactory.getLogger(QuestionService.class);
	@Autowired
	private QuestionMapper questDao;
	@Autowired
	private UserService userService;

	/**
	 * 生成新问题
	 * @param obj
	 * @param userName 
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public ResponseData<String> createQuestion(Question question, String userName) {
		ResponseData<String> result = new ResponseData<>();
		try {
			User user = userService.getUserInfoByUserName(userName);
			question.setQuestCreateTime(new Date());
			question.setQuestUpdateTime(new Date());
			question.setQuestUserId(user.getUserId());
			questDao.insert(question);
			userService.addUserScore(ScoreTypeConstans.ASK_QUESTION,user);
		} catch (Exception e) {
			result.setCode(ErrorConstans.QUESTION_CREATE_FAIL_CODE);
			result.setMessage(ErrorConstans.QUESTION_CREATE_FAIL_MSG);
			log.error(e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	/**
	 * 根据userId获取用户问题列表
	 * @param userId
	 * @return
	 */
	public List<Question> getQuestionsByUserId(Integer userId) {
		return questDao.selectQuestionsByUserId(userId);
	}

	/**
	 * 更新问题;
	 * @param Question question对象
	 * @return
	 */
	public ResponseData<String> updateQuestion(Question question) {
		ResponseData<String> result = new ResponseData<>();
		question.setQuestUpdateTime(new Date());
		try{
			questDao.updateQuestionById(question);
			result.setSuccess();
		}catch (Exception e) {
			result.setCode(ErrorConstans.QUESTIION_UPDATE_FAIL_CODE);
			result.setMessage(ErrorConstans.QUESTION_UPDATE_FAIL_MSG);
			log.error(e.toString());
		}
		return result;
	}

	/**
	 * 根据id获取数据展示
	 * @param questId
	 * @param userName
	 * @return
	 */
	public ResponseData<Question> getQuestionById(Integer questId, String userName) {
		ResponseData<Question> result = new ResponseData<>();
		User user = userService.getUserInfoByUserName(userName);
		Question question = questDao.selectQuestionsById(questId);
		if(user == null){
			result.setCode(ErrorConstans.USERNAME_NOT_EXIT_CODE);
			result.setMessage(ErrorConstans.USERNAME_NOT_EXIT_MSG);
			return result;
		}
		if(question == null){
			result.setCode(ErrorConstans.QUESTION_NOT_EXIT_CODE);
			result.setMessage(ErrorConstans.QUESTION_NOT_EXIT_MSG);
			return result;
		}
		if(question.getQuestUserId() != user.getUserId()){
			result.setMessage("当前用户与问题不匹配");
			return result;
		}
		result.setObject(question);
		result.setSuccess();
		
		return result;
	}

}
