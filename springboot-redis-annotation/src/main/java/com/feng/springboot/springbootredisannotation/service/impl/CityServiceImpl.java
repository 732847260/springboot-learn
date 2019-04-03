package com.feng.springboot.springbootredisannotation.service.impl;

import com.feng.springboot.springbootredisannotation.damain.City;
import com.feng.springboot.springbootredisannotation.dao.CityDao;
import com.feng.springboot.springbootredisannotation.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 23:55 2019/3/1
 * @Modified By: LiangZF
 */
@Service
@CacheConfig(cacheNames="cityCaches")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    /*

        key的格式："cityCaches::userCache3"
       1. 必须 key相同，这样才可以在更新和查找数据，删除的时候，保证是同一条数据
       2.CachePut 更新缓存的时候，更新的是方法返回的值，一般的update返回的是所更新的行，要改成和查找数据一样的对象类型才可以

    @Cacheable支持如下几个参数：

        value：缓存位置的一段名称，不能为空

        key：缓存的key，默认为空，表示使用方法的参数类型及参数值作为key，支持SpEL

        keyGenerator：指定key的生成策略

        condition：触发条件，满足条件就加入缓存，默认为空，表示全部都加入缓存，支持SpEL

 

    @CacheEvict支持如下几个参数：

        value：缓存位置的一段名称，不能为空

        key：缓存的key，默认为空，表示使用方法的参数类型及参数值作为key，支持SpEL

        condition：触发条件，满足条件就加入缓存，默认为空，表示全部都加入缓存，支持SpEL

        allEntries：true表示清除value中的全部缓存，默认为false

       在@CacheConfig(cacheNames="cityCaches")可以全局设置缓存的value

     */
    @Cacheable(key = "'userCache' + #p0")
    @Override
    public City findCityById(Long id) {
        System.out.println("执行这里，说明缓存中读取不到数据，直接读取数据库...."+cityDao.findById(id));
        return cityDao.findById(id);
    }

    @Override
    public Long saveCity(City city) {
        return cityDao.saveCity(city);
    }

    @CachePut(key = "'userCache'+#p0.id")
    @Override
    public City updateCity(City city) {
        System.out.println("执行这里，更新数据库，更新缓存....");
        cityDao.updateCity(city);
        return cityDao.findById(city.getId());
    }

    @CacheEvict(key = "'userCache'+#p0")
    @Override
    public Long deleteCity(Long id) {
        System.out.println("删除成功");
        return cityDao.deleteCity(id);
    }

    @Override
    public List<City> getList() {
        return cityDao.getList();
    }

}
