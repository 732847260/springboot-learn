package com.springboot.poi.poiController;




import com.springboot.poi.utils.DateUtil;
import com.springboot.poi.utils.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.springboot.poi.entity.Person;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 16:38 2019/4/1
 * @Modified By: LiangZF
 */
@RestController
public class PoiTest {

    @RequestMapping("export")
    public void exprot(HttpServletResponse response){
        // 模拟获取需要导出的数据
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person("路飞","1",new Date());
        Person person2 = new Person("娜美","2", DateUtil.addDays(new Date(),3));
        Person person3 = new Person("索隆","1", DateUtil.addDays(new Date(),10));
        Person person4 = new Person("小狸猫","1", DateUtil.addDays(new Date(),-10));
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        System.out.println("----------------");
        //导出操作
        FileUtil.exportExcel(personList,"花名册","草帽一伙",Person.class,"海贼王.xlsx",response);

    }

    @RequestMapping("importExcel")
    public void importExcel(){
        String filePath = "F:\\海贼王.xls";
        //解析excel，
        List<Person> personList = FileUtil.importExcel(filePath,1,1,Person.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");
        for(Person person:personList){
            System.out.println(person.toString());
//            Date date = new Date();
           // System.out.println(person.getBirthday());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            String formatDate = df.format(person.getBirthday());
            System.out.println(formatDate);

        }
        //TODO 保存数据库
    }
}
