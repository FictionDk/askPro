package org.fictio.askPro.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.fictio.askPro.pojo.Commend;
@Mapper
public interface CommendMapper {
    int insert(Commend record);

    int insertSelective(Commend record);

    /**
     * 根据userId及answerId查找commend
     * @param userId
     * @param answerId
     * @return
     */
	Commend selectCommendByUser(@Param("userId")int userId, @Param("answerId")int answerId);

	/**
	 * 更新commend
	 * @param commend
	 */
	void updateCommend(Commend commend);
}