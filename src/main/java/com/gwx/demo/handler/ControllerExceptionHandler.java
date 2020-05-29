package com.gwx.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());//使用指定的类XXX初始化日志对象，方便在日志输出的时候，可以打印出日志信息所属的类。

    /**
     * 示例：protected static final Logger logger = LoggerFactory.getLogger(XXX.class);
     *
     *           logger.debug("hello world");
     *
     * 输出：XXX:hello world
     * **/
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e)throws Exception{
        logger.error("Request URL: {} , Exception : {}",request.getRequestURL(),e);

        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){// 从某个类获取某个annotation
            throw  e;
        }

        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }

}
