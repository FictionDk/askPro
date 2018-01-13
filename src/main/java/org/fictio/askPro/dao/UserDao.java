package org.fictio.askPro.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.fictio.askPro.pojo.User;
@Mapper
public interface UserDao {
	User selectUserByUsername(@Param("userName")String userName);
	
	int updateUserLastLoginTime(User user);
	
    int insert(User record);

    int insertSelective(User record);
    
    int updateUserScore(User user);

	User queryObject(@Param("userId")Integer userId);
}