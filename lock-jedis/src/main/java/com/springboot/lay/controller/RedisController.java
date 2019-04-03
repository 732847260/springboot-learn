package com.springboot.lay.controller;

import com.springboot.lay.config.RedisConfig;
import com.springboot.lay.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 15:29 2019/3/29
 * @Modified By: LiangZF
 */
@Controller
@RequestMapping(value="/redis")
public class RedisController {
    @Autowired
    RedisUtil redisUtil;
    private static Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

    /**
     * @auther: zhangyingqi
     * @date: 16:23 2018/8/29
     * @param: []
     * @return: org.springframework.ui.ModelMap
     * @Description: 执行redis写/读/生命周期
     */

    /*
     *
     * @Description: 
     * 
     * @auther: LiangZF
     * @date: 9:49 2019/4/1 
     * @param: []
     * @return: java.lang.String
     *
     */
    @RequestMapping(value = "getRedis",method = RequestMethod.GET)
    @ResponseBody
    public String getRedis(){
        redisUtil.set("20182018","这是一条测试数据", 0);
        Long resExpire = redisUtil.expire("20182018", 60, 0);//设置key过期时间
        LOGGER.info("resExpire="+resExpire);
        String res = redisUtil.get("20182018", 0);
        return res;
    }

}

