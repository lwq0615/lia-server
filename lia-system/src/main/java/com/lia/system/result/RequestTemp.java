package com.lia.system.result;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 将request请求体暂存，因为404会导致原request丢失
 */
@Scope("request")
@Component
public class RequestTemp {

    private HttpServletRequest request;

    public void setReq(HttpServletRequest request){
        this.request = request;
    }

    public HttpServletRequest getReq(){
        return this.request;
    }

}
