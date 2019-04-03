package com.feng.springboot.springbootredisannotation.dao;

import com.feng.springboot.springbootredisannotation.damain.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 23:23 2019/3/1
 * @Modified By: LiangZF
 */
public interface CityDao {
    City findById(@Param("id") Long id);

    Long saveCity(City city);

    Integer updateCity(City city);

    Long deleteCity(Long id);

    List<City> getList();
}
