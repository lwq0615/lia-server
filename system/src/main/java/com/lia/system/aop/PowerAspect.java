package com.lia.system.aop;


import com.alibaba.fastjson.JSONObject;
import com.lia.system.annotation.PreAuthorize;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.tool.Jwt;
import io.jsonwebtoken.ExpiredJwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * 通过aop做权限判断
 */
@Aspect
@Component
public class PowerAspect {

    @Autowired
    private Jwt jwt;
    @Value("${token.header}")
    private String header;
    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;


    /**
     * 拦截添加了@GetMapping,@PostMapping,@PutMapping,@DeleteMapping,@RequestMapping注解的所有方法
     * 在进入方法前进行权限校验
     * @return Http请求返回值
     */
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object get(ProceedingJoinPoint joinPoint) throws Throwable{
        return this.arount(joinPoint);
    }
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object post(ProceedingJoinPoint joinPoint) throws Throwable{
        return this.arount(joinPoint);
    }
    @Around("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public Object put(ProceedingJoinPoint joinPoint) throws Throwable{
        return this.arount(joinPoint);
    }
    @Around("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object delete(ProceedingJoinPoint joinPoint) throws Throwable{
        return this.arount(joinPoint);
    }
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object request(ProceedingJoinPoint joinPoint) throws Throwable{
        return this.arount(joinPoint);
    }


    /**
     * 在该方法内处理token，并判断用户是否有访问方法的权限
     * @return Http请求返回值
     */
    private Object arount(ProceedingJoinPoint joinPoint) throws Throwable {
        //从获取RequestAttributes中获取HttpServletRequest的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        response.setCharacterEncoding("UTF-8");
        //遇到错误，交给HTTP异常处理
        if(request.getRequestURI().equals(errorPath)){
            throw new HttpException(response.getStatus());
        }else{
            //获取执行方法是否携带@PreAuthorize注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            int httpCode = this.tokenParse(request);
            //携带@PreAuthorize注解不校验权限
            PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
            if(preAuthorize != null && preAuthorize.value() == PreAuthorize.Status.all){
                // 解析参数并放行
                return joinPoint.proceed();
            }
            else if(preAuthorize != null && preAuthorize.value() == PreAuthorize.Status.login && httpCode == 200){
                return joinPoint.proceed();
            }
            else if(httpCode == 200){
                // 不携带@PreAuthorize注解则正常判断权限
                if(request.getAttribute("sysPower") != null){
                    List<Map> powers = (List<Map>) request.getAttribute("sysPower");
                    //判断是否有访问该接口的权限
                    for (Map power : powers) {
                        if(power.get("url").equals(request.getRequestURI())){
                            return joinPoint.proceed();
                        }
                    }
                    throw new HttpException(403);
                }else{
                    throw new HttpException(403);
                }
            }else{
                throw new HttpException(httpCode);
            }
        }
    }


    /**
     * 解析request中的token，将参数存入request，后续开发中
     * 可以通过request对象获取登录用户的信息，角色和权限
     * @param request 请求对象
     * @return 可能应该响应的状态码
     */
    private int tokenParse(HttpServletRequest request){
        String token = request.getHeader(header);
        // 没有登录
        if(token == null || token.equals("")){
            return 401;
        }
        //解析token
        try{
            Map map = jwt.parse(token);
            if(map == null){
                return 401;
            }
            if(map.get("sysUser") != null){
                //登录成功
                SysUser user = JSONObject.parseObject(JSONObject.toJSONString(map.get("sysUser")),SysUser.class);
                request.setAttribute("sysUser",user);
                if(map.get("sysRole") != null){
                    request.setAttribute("sysRole",JSONObject.parseObject(JSONObject.toJSONString(map.get("sysRole")),SysRole.class));
                }
                if(map.get("sysPower") != null){
                    request.setAttribute("sysPower",map.get("sysPower"));
                }
                return 200;
            }else{
                return 401;
            }
        }catch (ExpiredJwtException je){
            //token过期
            return 402;
        }catch (Exception e){
            // token解析失败，当做未登录处理
            return 401;
        }
    }

}
