package com.lia.system.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringUtils {


    @Autowired
    private static ApplicationContext applicationContext;


    public static ApplicationContext getContext(){
        return applicationContext;
    }

}
