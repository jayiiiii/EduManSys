package com.example.springboot.service;

import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */
public interface IUserService extends IService<User> {

    User login(User user);
}
