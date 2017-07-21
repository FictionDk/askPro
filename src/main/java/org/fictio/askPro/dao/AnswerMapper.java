package org.fictio.askPro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fictio.askPro.pojo.Answer;

public interface AnswerMapper {
    int insert(Answer record);

    int insertSelective(Answer record);

	List<Answer> selectAnswerListByQuestId(@Param("questId")int questId,
			@Param("pageStart")int pageStart,@Param("pageSize")int pageSize);
	
	Answer selectAnswerByUser(@Param("questId")int questId, @Param("userId")int userId);
}