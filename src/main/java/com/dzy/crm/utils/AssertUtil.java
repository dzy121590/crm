package com.dzy.crm.utils;

import com.dzy.crm.exceptions.ParamsException;

/**
 * @author 董志远
 */
public class AssertUtil {


    public  static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }

}
