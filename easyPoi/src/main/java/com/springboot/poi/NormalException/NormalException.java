package com.springboot.poi.NormalException;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 16:33 2019/4/1
 * @Modified By: LiangZF
 */
public class NormalException extends RuntimeException{
    private static final long serialVersionUID =1L;
    public NormalException(String message) {
        super(message);
    }

}
