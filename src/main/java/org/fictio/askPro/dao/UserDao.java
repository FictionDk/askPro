package org.fictio.askPro.dao;

import org.fictio.askPro.pojo.User;
public interface UserDao {
	User selectUserByUsername(String userName);
	
	int updateUserLastLoginTime(User user);
	
    int insert(User record);

    int insertSelective(User record);
    
    int updateUserScore(User user);
}