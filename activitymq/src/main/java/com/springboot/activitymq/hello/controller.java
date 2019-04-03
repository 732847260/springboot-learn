package com.springboot.activitymq.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 16:42 2019/2/26
 * @Modified By: LiangZF
 */
@RestController
public class controller {
    @RequestMapping("hello")
    @ResponseBody
    public String sayHello(){
        return "hello weiwei";
    }
}
