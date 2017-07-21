package org.fictio.askPro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fictio.askPro.pojo.Question;

public interface QuestionMapper {
    int insert(Question record);

    int insertSelective(Question record);
    
    List<Question> selectQuestionsByUserId(int userId);
    
	List<Question> selectQuestionsByPage(@Param("userId")int userId,
			@Param("pageStart")int page,@Param("pageSize")int pageSize);
    
    Question selectQuestionsById(int questId);
    
    void updateQuestionById(Question question);

	List<Question> selectAllQuestionsByPage(@Param("pageStart")int pageStart,
			@Param("pageSize")int pageSize);

}