package org.fictio.askPro.dao;

import java.util.List;

import org.fictio.askPro.pojo.Question;

public interface QuestionMapper {
    int insert(Question record);

    int insertSelective(Question record);
    
    List<Question> selectQuestionsByUserId(int userId);
    
    Question selectQuestionsById(int questId);
    
    void updateQuestionById(Question question);

}