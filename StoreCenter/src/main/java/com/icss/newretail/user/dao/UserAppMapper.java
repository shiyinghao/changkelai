package com.icss.newretail.user.dao;

import com.icss.newretail.model.App;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAppMapper {
    List<App> getAppsByUserId(@Param("userId") String userId);
}
