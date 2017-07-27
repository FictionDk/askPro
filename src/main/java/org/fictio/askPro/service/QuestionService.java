package org.fictio.askPro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fictio.askPro.constans.CommendConstans;
import org.fictio.askPro.constans.ErrorConstans;
import org.fictio.askPro.constans.ScoreTypeConstans;
import org.fictio.askPro.dao.AnswerMapper;
import org.fictio.askPro.dao.CommendMapper;
import org.fictio.askPro.dao.QuestionMapper;
import org.fictio.askPro.pojo.Answer;
import org.fictio.askPro.pojo.AnswerReq;
import org.fictio.askPro.pojo.AnswerResp;
import org.fictio.askPro.pojo.Commend;
import org.fictio.askPro.pojo.Page;
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
	private AnswerMapper answerDao;
	@Autowired
	private CommendMapper commendDao;
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
		User user = userService.getUserInfoByUserName(userName);
		try {
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
		result.setObject(question);
		result.setSuccess();
		
		return result;
	}

	/**
	 * 分页获取用户的问题列表
	 * @param userName
	 * @param obj
	 * @return
	 */
	public ResponseData<Page<Question>> getQuestionListByUser(String userName, Integer pageStart) {
		ResponseData<Page<Question>> result = new ResponseData<>();
		User user = userService.getUserInfoByUserName(userName);
		if(user != null){
			Page<Question> page = new Page<>();
			List<Question>questionList = questDao.selectQuestionsByPage(user.getUserId(),pageStart,page.getPageSize());
			page.setCountSize(questionList.size());
			page.setPageContent(questionList);
			page.setPageStart(pageStart);
			result.setObject(page);
			result.setSuccess();
		}else{
			throw new RuntimeException("获取失败");
		}
		return result;
	}

	/**
	 * 所有问题展示--扩展,根据用户调整排序;
	 * @param token
	 * @param obj
	 * @return
	 */
	public ResponseData<Page<Question>> getQuestionShow(String token, Integer pageStart) {
		ResponseData<Page<Question>> result = new ResponseData<>();
		Page<Question> page = new Page<>();
		page.setPageStart(pageStart);
		List<Question> questionList = questDao.selectAllQuestionsByPage(pageStart, page.getPageSize());
		page.setCountSize(questionList.size());
		page.setPageContent(questionList);
		
		result.setSuccess();
		result.setObject(page);
		return result;
	}

	/**
	 * 获取回答列表
	 * @param userName
	 * @param answerReq
	 * @return
	 */
	public ResponseData<Page<AnswerResp>> getQuestionAnswerList(String userName, AnswerReq answerReq) {
		ResponseData<Page<AnswerResp>> result = new ResponseData<>();
		Page<AnswerResp> page = new Page<>();
		List<AnswerResp> resultList = new ArrayList<>();
		
		List<Answer> answerList = answerDao.selectAnswerListByQuestId(answerReq.getQuestId(),
				answerReq.getPageStart(),page.getPageSize());
		
		for (Answer answer : answerList) {
			AnswerResp answerResult = new AnswerResp();
			answerResult.setAnswer(answer);
			answerResult.setAnswerStatus(getAnswerCommendStatus(userName, answer.getAnswerId()));
			resultList.add(answerResult);
		}
		
		page.setPageContent(resultList);
		page.setCountSize(answerList.size());
		result.setObject(page);
		result.setSuccess();
		return result;
	}
	
	/**
	 * 获取当前用户当前答案点赞状态
	 * @param userName
	 * @param answerId
	 * @return 0非赞/1点赞
	 */
	private String getAnswerCommendStatus(String userName,int answerId){
		String result = CommendConstans.IS_COMMEND_STR;
		User user = userService.getUserInfoByUserName(userName);
		Commend commned = commendDao.selectCommendByUser(user.getUserId(), answerId);
		if(commned == null){
			result = CommendConstans.NOT_COMMEND_STR;
		}else{
			result = commned.getCommendStatus() == 
					CommendConstans.IS_COMMEND ? CommendConstans.IS_COMMEND_STR
							:CommendConstans.NOT_COMMEND_STR;
		}
		return result;
	}

	/**
	 * 回答问题
	 * @param answer
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public ResponseData<String> answerQuestion(Answer answer) {
		ResponseData<String> result = new ResponseData<>();
		User user = userService.getUserInfoByUserName(answer.getUserName());
		log.info(user.toString());
		//测试期间屏蔽一个用户只能回答一次功能
		/*Answer temp = answerDao.selectAnswerByUser(answer.getQuestionId(), user.getUserId());
		if(temp != null){
			result.setCode(ErrorConstans.QUESTION_HAS_BEAN_ANSWER_CODE);
			result.setMessage(ErrorConstans.QUESTION_HAS_BEAN_ANSWER_MSG);
			return result;
		}*/
		answer.setUserId(user.getUserId());
		answer.setAnswerCreateTime(new Date());
		answer.setAnswerUpdateTime(new Date());
		log.info(answer.toString());
		answerDao.insert(answer);
		userService.addUserScore(ScoreTypeConstans.ANSWER_QUESTION, user);
		result.setSuccess();
		return result;
	}

	/**
	 * 给答案点赞/取消赞
	 * @param userName
	 * @param obj
	 * @return
	 */
	@Transactional
	public ResponseData<String> commendAnswer(String userName, Integer answerId) {
		ResponseData<String> result = new ResponseData<>();
		boolean commendCancel = false;
		User user = userService.getUserInfoByUserName(userName);
		Commend temp = commendDao.selectCommendByUser(user.getUserId(),answerId);
		if(temp == null){
			temp = insertNewCommend(answerId, user);
		}else{
			commendCancel = updateCommend(temp);
		}
		updateAnswerCommendCount(commendCancel,answerId);
		result.setObject(temp.getCommendStatus()+"");
		result.setSuccess();
		return result;
	}
	
	/**
	 * 更新当前commend状态;
	 * @param commend
	 */
	private boolean updateCommend(Commend commend){
		boolean commendCancel = false;
		if(commend.getCommendStatus() == (CommendConstans.IS_COMMEND)){
			commend.setCommendStatus(CommendConstans.NOT_COMMEND);
			commendCancel = true;
		}else{
			commend.setCommendStatus(CommendConstans.IS_COMMEND);
		}
		commend.setCommendTime(new Date());
		commendDao.updateCommend(commend);
		return commendCancel;
	}
	
	/**
	 * 添加新的赞
	 * @param answerId
	 * @param user
	 */
	private Commend insertNewCommend(int answerId,User user){
		Commend commend = new Commend();
		commend.setAnswerId(answerId);
		commend.setUserId(user.getUserId());
		commend.setUserName(user.getUserName());
		commend.setCommendTime(new Date());
		commend.setCommendStatus(CommendConstans.IS_COMMEND);
		commendDao.insert(commend);
		return commend;
	}
	
	/**
	 * 更新回答赞的数量
	 * @param commendCancel 赞是否取消
	 * @param answerId 回答的id
	 */
	private void updateAnswerCommendCount(boolean commendCancel,int answerId){
		int count = 1;
		if(commendCancel){
			count = -1;
		}
		answerDao.updateAnswerCommendCount(answerId, count);
	}
}
