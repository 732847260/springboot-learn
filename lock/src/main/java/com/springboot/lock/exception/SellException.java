package com.springboot.lock.exception;

import com.springboot.lock.enums.ResultEnum;
import lombok.Data;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 22:33 2019/3/27
 * @Modified By: LiangZF
 */
@Data
public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


    public SellException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code=code;
    }
}
