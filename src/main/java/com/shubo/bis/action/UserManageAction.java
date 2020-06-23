package com.shubo.bis.action;

import com.shubo.bis.constants.ErrorCode;
import com.shubo.bis.service.UerManageService;
import com.shubo.bis.util.cache.BaseLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/4,16:59
 */
@RestController
@RequestMapping("demo")
public class UserManageAction {
    @Autowired
    private UerManageService uerManageService;

    @RequestMapping("index/{id}")
    public String findByCompanyID(@PathVariable int id) {
        if (StringUtils.isEmpty(id)) {
            //todo
            BaseLog.getDailyLog().info("请求错误，{}，{}"+ErrorCode.PRAAM_ERROR.getCode(),ErrorCode.PRAAM_ERROR.getDesc());
        }
        return uerManageService.findById(id).toString();
    }

}
