package com.springboot.poi.utils;

import com.springboot.poi.NormalException.NormalException;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Author: LiangZF
 * @Description: 导入导出工具类
 * @Date: Created in 16:25 2019/4/1
 * @Modified By: LiangZF
 */
public class FileUtil {
   /*  
    * @Description  导出Excel
    * @Author LiangZF
    * @param list 数据集合
    * @param title 表格标题
    * @param sheetName 纸张名称
    * @param pojoClass 导出对象
    * @param fileName 文件名称
    * @param isCreateHeader 否创建表头
    * @param response 响应对象
    * @Date 2019/4/2 11:41
    * @return void
    */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }
  /*
   * @Description 导出Excel
   * @Author LiangZF
   * @param list 数据集合
   * @param title 表格标题
   * @param sheetName 纸张名称
   * @param pojoClass 导出对象
   * @param fileName 文件名称
   * @param response 响应对象
   * @Date 2019/4/2 11:42
   * @return void
   */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }
    /*
     * @Description 导出Excel
     * @Author LiangZF
     * @param list 数据集合
     * @param fileName 文件名称
     * @param response 响应对象
     * @Date 2019/4/2 11:44
     * @return void
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }
    /*
     * @Description   默认导出
     * @Author LiangZF
     * @param list 数据集合
     * @param pojoClass 导出对象
     * @param fileName 文件名称
     * @param response 响应对象
     * @param exportParams 导出条件
     * @Date 2019/4/2 11:45
     * @return void
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }
    /*
     * @Description  下载Excel文件格式
     * @Author LiangZF
     * @param fileName 文件名称带后缀
     * @param response 响应对象
     * @param workbook 工作书对象
     * @Date 2019/4/2 11:46
     * @return void
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new NormalException(e.getMessage());
        }
    }
    /*
     * @Description  下载Excel文件
     * @Author LiangZF
     * @param list 数据集合
     * @param fileName 文件名称带后缀
     * @param response
     * @Date 2019/4/2 12:14
     * @return void
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new NormalException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NormalException(e.getMessage());
        }
        return list;
    }
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new NormalException("excel文件不能为空");
        } catch (Exception e) {
            throw new NormalException(e.getMessage());
        }
        return list;
    }

}
