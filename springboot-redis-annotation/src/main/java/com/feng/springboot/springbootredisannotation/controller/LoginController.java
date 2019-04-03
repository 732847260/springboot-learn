package com.feng.springboot.springbootredisannotation.controller;

import com.feng.springboot.springbootredisannotation.damain.User;
import com.feng.springboot.springbootredisannotation.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 15:39 2019/3/22
 * @Modified By: LiangZF
 */
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/userlogin")
    public String Login(HttpServletRequest request,String username,String pwd){
        User user = userService.getUserByUserName(username);
        if(user != null && user.getPassword().equals(pwd)){
            request.getSession().setAttribute("useraccount",user);
            return "登录成功";
        }
        return "登录失败";
    }

    @RequestMapping(value = "/userlogout")
    public String logout (HttpServletRequest request){
        HttpSession session=request.getSession();
        session.removeAttribute("useraccount");
        return "退出登录";
    }
    @RequestMapping(value = "/getMsg")
    public String getMsg(){
        return  "fengwei";
    }

    @RequestMapping(value = "/getStream")
    public String streams(){
        List<String> strings = Arrays.asList("Hollis", "HollisChuang", "hollis", "Hello", "HelloWorld", "Hollis1");
        strings =  strings.stream().filter(string->string.contains("Hollis")).collect(Collectors.toList());
        System.out.println(strings);
        String[] arr = new String[]{"yes","YES","no","NO"};
        List<String> arrs = Arrays.stream(arr).map(x -> x.toUpperCase()).collect(Collectors.toList());
        System.out.println(arrs);
        Integer[] intArr = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        List<Integer> intList =  Arrays.stream(intArr).filter(x -> x>3 && x<8).collect(Collectors.toList());
        System.out.println(intList);
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"e", "f", "c", "d"};
        String[] arr3 = {"h", "j", "c", "d"};
        // Stream.of(arr1, arr2, arr3).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
        return "";
    }
    @RequestMapping(value = "/testSort")
    public void testSort(){
        String[] arr1 = {"abc","a","bc","abcd"};
        Arrays.stream(arr1).sorted((x,y)->{
            if(x.length() > y.length())
                return 1;
            else if(x.length()<y.length())
                return -1;
            else
                return 0;
        }).forEach(System.out::println);
        System.out.println("-------------------------------");
        Arrays.stream(arr1).sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
        System.out.println("-------------------------------");
        Arrays.stream(arr1).sorted(Comparator.reverseOrder()).forEach(System.out::println);
        System.out.println("-------------------------------");
        Arrays.stream(arr1).sorted(Comparator.naturalOrder()).forEach(System.out::println);
    }
    @RequestMapping(value = "/testCount")
    public void testCount(){
        String[] arr1 = {"abc","a","bc","abcd"};
        List<String> list =  Arrays.stream(arr1).collect(Collectors.toList());
        list.forEach((x) -> System.out.println(x));
    }


}
