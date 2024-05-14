package com.example.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWTVerifier;
//拦截器
public class JwtInterceptor implements HandlerInterceptor {
//
//    @Autowired
//
//    private IUserService userService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String token = request.getHeader("token");
//        // 如果不是映射到方法直接通过
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        if (StrUtil.isBlank(token)) {
//            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
//        }
//        // 获取 token 中的 id
//        String userId;
//        try {
//            userId = JWT.decode(token).getAudience().get(0);
//        } catch (JWTDecodeException j) {
//            throw new ServiceException(Constants.CODE_401, "token验证失败");
//        }
//        //根据token中的id查询数据库
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("wjy_id",userId);
//       User user= userService.getOne(queryWrapper);
//
//        if (user == null) {
//            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
//        }
//        // 用户密码加签 验证 token
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getWjy_upassword())).build();
//        try {
//            jwtVerifier.verify(token);
//        } catch (JWTVerificationException e) {
//            throw new ServiceException(Constants.CODE_401, "验证失败，请重新登录");
//        }
//        return true;
//    }

}
