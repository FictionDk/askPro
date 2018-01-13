package org.fictio.askPro.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.fictio.askPro.constans.ErrorConstans;
import org.fictio.askPro.constans.NotifyTypeConstans;
import org.fictio.askPro.constans.ScoreTypeConstans;
import org.fictio.askPro.dao.UserDao;
import org.fictio.askPro.pojo.Question;
import org.fictio.askPro.pojo.ResponseData;
import org.fictio.askPro.pojo.User;
import org.fictio.askPro.pojo.UserIndex;
import org.fictio.askPro.pojo.UserLogin;
import org.fictio.askPro.pojo.event.QuestionCreateEvent;
import org.fictio.askPro.util.CacheManager;
import org.fictio.askPro.util.NotifyClientProxy;
import org.fictio.askPro.util.NotifyManager;
import org.fictio.askPro.util.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class UserService implements ApplicationListener<QuestionCreateEvent> {
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuestionService questService;
	@Autowired
	private TokenManager tokenManage;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	/**
	 * 发送注册短信码
	 * @param u
	 * @return
	 */
	public boolean registeCode(User u) {
		String code = String.valueOf((new Random().nextInt(9000) + 1000));
		CacheManager.getCacheInstance().setValue(u.getUserMobile(), code);
		try {
			NotifyClientProxy np = new NotifyClientProxy();
			NotifyManager nm = (NotifyManager) np.createInstance();
			nm.sendNotify(u.getUserMobile(), code, NotifyTypeConstans.REGISTER_TYPE);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 用户注册业务逻辑操作
	 * @param user
	 * @param code
	 * @return
	 */
	public ResponseData<String> registeByMobile(User user, String code) {
		ResponseData<String> result = new ResponseData<>();
		String cacheCode = CacheManager.getCacheInstance().getValue(user.getUserMobile());
		if(cacheCode.equals(code)){
			user.setUserName(createUserName(user.getUserName()));
			user.setCreateTime(new Date());
			user.setLastLoginTime(new Date());
			userDao.insert(user);
			log.info(user.toString());
			String token = tokenManage.createToken(user.getUserName());
			log.info("生成token:"+token);
			result.setSuccess();
			result.setToken(token);
		}else{
			result.setMessage(ErrorConstans.REGISTE_ACT_ERROR_MSG);
			result.setCode(ErrorConstans.REGISTE_ACT_ERROR_MSG);
		}
		return result;
	}
	
	/**
	 * 对用户名简单去重
	 * 
	 * @param userName
	 * @return
	 */
	private String createUserName(String userName){
		User tempUser = userDao.selectUserByUsername(userName);
		if(tempUser != null){
			userName += String.valueOf((new Random().nextInt(90)+10));
			createUserName(userName);
		}
		return userName;
	}

	/**
	 * 用户登录
	 * @param u
	 * @return
	 */
	public ResponseData<String> userLogin(UserLogin u) {
		ResponseData<String> responseData = new ResponseData<>();
		User tempUser = userDao.selectUserByUsername(u.getUserName());
		if(tempUser == null){
			responseData.setCode(ErrorConstans.USERNAME_NOT_EXIT_CODE);
			responseData.setMessage(ErrorConstans.USERNAME_NOT_EXIT_MSG);
		}else if(!tempUser.getPassword().equals(u.getPassword())){
			responseData.setCode(ErrorConstans.USER_PASSWORD_NO_MATCHED_COD);
			responseData.setMessage(ErrorConstans.USER_PASSWORD_NO_MATCHED_MSG);
		}else{
			String token = tokenManage.createToken(u.getUserName());
			log.info(String.valueOf(tempUser==null));
			tempUser.setLastLoginTime(new Date());
			userDao.updateUserLastLoginTime(tempUser);
			log.info("生成token:"+token);
			responseData.setSuccess();
			responseData.setToken(token);
		}
		return responseData;
	}

	/**
	 * 用户个人中心页面展示资料获取
	 * @param token
	 * @return
	 */
	public ResponseData<UserIndex> getUserIndexInfo(String token) {
		ResponseData<UserIndex> responseData = new ResponseData<>();
		UserIndex userIndex = new UserIndex();
		String userName = tokenManage.getTokenValue(token);
		User u = userDao.selectUserByUsername(userName);
		if(u == null){
			responseData.setCode(ErrorConstans.TOKEN_ERROR_CODE);
			responseData.setMessage(ErrorConstans.TOKEN_ERROR_MSG);
			return responseData;
		}
		userIndex.setUser(u);
		List<Question> questions = questService.getQuestionsByUserId(u.getUserId());
		userIndex.setQuestionCount(questions.size());
		userIndex.setQuestionList(questions);
		responseData.setObject(userIndex);
		responseData.setSuccess();
		return responseData;
	}

	public User getUserInfoByUserName(String userName) {
		return userDao.selectUserByUsername(userName);
	}

	/**
	 * 用户退出登录
	 * @param token
	 * @param userName
	 * @return
	 */
	public ResponseData<String> userLoginOut(String token, String userName) {
		ResponseData<String> result = new ResponseData<>();
		String uName = tokenManage.getTokenValue(token);
		if(Strings.isNullOrEmpty(uName) && uName.equals(userName)){
			result.setSuccess();
		}else{
			result.setMessage("退出登录失败");
		}
		return result;
	}

	/**
	 * 更新用户积分
	 * 
	 * @param user 
	 * @param askQuestion
	 */
	public void addUserScore(String scoreType, User user) {
		switch (scoreType) {
		case ScoreTypeConstans.ASK_QUESTION:
			user.setUserScore(ScoreTypeConstans.ASK_QUESTION_SCORE);
			userDao.updateUserScore(user);
			break;
		case ScoreTypeConstans.ANSWER_QUESTION:
			user.setUserScore(ScoreTypeConstans.ANSWER_QUESTION_SCORE);
			userDao.updateUserScore(user);
			break;
		default:
			throw new RuntimeException("当前积分类型不存在");
		}
	}

	@Override
	public void onApplicationEvent(QuestionCreateEvent event) {
		Question question = (Question) event.getSource();
		User user = userDao.queryObject(question.getQuestUserId());
		user.setUserScore(ScoreTypeConstans.ASK_QUESTION_SCORE);
		userDao.updateUserScore(user);		
	}
}
