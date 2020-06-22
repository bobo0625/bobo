package com.shubo.base.mapper;

import com.shubo.base.dao.UserInfo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/4,17:09
 */
@Repository
public interface UserManageMapper {
    UserInfo findById(@Param("uaId") int uaId);
}