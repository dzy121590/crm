package com.dzy.crm;

import com.alibaba.fastjson.JSON;
import com.dzy.crm.base.ResultInfo;
import com.dzy.crm.exceptions.NoLoginException;
import com.dzy.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 董志远
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        // 设置默认异常处理
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("code", 400);
        mv.addObject("msg", "系统异常，请稍后再试...");
        /**
         * 判断异常类型
         * 如果是未登录异常，则先执⾏相关的拦截操作
         */
        if (ex instanceof NoLoginException) {
            // 如果捕获的是未登录异常，则重定向到登录⻚⾯
             mv.setViewName("no_login");
             mv.addObject("msg","用户未登录");
             mv.addObject("ctx",request.getContextPath());
            return mv;
        }
        // 判断 HandlerMethod
        if (handler instanceof HandlerMethod) {
            // 类型转换
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取⽅法上的 ResponseBody 注解
            ResponseBody responseBody =
                    handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            // 判断 ResponseBody 注解是否存在 (如果不存在，表示返回的是视图;如果存在，表示返回的是JSON)
            if (null == responseBody) {
                /**
                 * ⽅法返回视图
                 */
                if (ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    mv.addObject("code", pe.getCode());
                    mv.addObject("msg", pe.getMsg());
                }
                return mv;
            } else {
                /**
                 * ⽅法上返回JSON
                 */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统异常，请重试！");
                // 如果捕获的是⾃定义异常
                if (ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                // 设置响应类型和编码格式 （响应JSON格式）
                response.setContentType("application/json;charset=utf-8");
                // 得到输出流
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    // 将对象转换成JSON格式，通过输出流输出
                    out.write(JSON.toJSONString(resultInfo));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return null;
            }
        }
        return mv;
    }
}
