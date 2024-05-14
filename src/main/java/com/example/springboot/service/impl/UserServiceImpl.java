package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();

    @Override
    public User login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wjy_id", user.getWjyId());
        queryWrapper.eq("wjy_upassword", user.getWjyUpassword());

        User one;
        // 处理异常情况
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        } if (one != null) {
            BeanUtil.copyProperties(one, user, true);
//            String token= TokenUtils.genToken(one.getWjy_id(),one.getWjy_upassword());
//            user.setWjy_token(token);
            return user;
        } else {
            throw new ServiceException(Constants.CODE_600,"登录名或密码错误");
        }

    }
}
