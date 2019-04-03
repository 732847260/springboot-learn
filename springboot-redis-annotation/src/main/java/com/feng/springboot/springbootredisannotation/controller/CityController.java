package com.feng.springboot.springbootredisannotation.controller;

import com.feng.springboot.springbootredisannotation.damain.City;
import com.feng.springboot.springbootredisannotation.service.CityService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 0:33 2019/3/2
 * @Modified By: LiangZF
 */
@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public City findCityById(@PathVariable("id") Long id){
        return cityService.findCityById(id);
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.PUT)
    public void updateCity(@RequestBody City city){
         cityService.updateCity(city);
    }
    @RequestMapping(value = "/api/delete/{id}", method = RequestMethod.GET)
    public void deleteCity(@PathVariable("id") Long id){
        cityService.deleteCity(id);
    }

    @RequestMapping(value = "/api/add", method = RequestMethod.POST)
    public void saveCity(@RequestBody City city){
        cityService.saveCity(city);
    }

    @ResponseBody
    @RequestMapping(value="/getList",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
    public List<City> getList(){
        return cityService.getList();
    }

    @ResponseBody
    @RequestMapping(value="/test",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
    public String test(){
        return "OK!";
    }
}
