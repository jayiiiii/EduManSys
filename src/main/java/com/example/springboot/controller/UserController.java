package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IUserService;
import com.example.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping
    public Boolean save(@RequestBody User user) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_id", user.getWjyId());
        queryWrapper.eq("wjy_type", user.getWjyType());
                return userService.update(user,queryWrapper)|| userService.save(user);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable String id) {
        return userService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
        }

    @GetMapping
    public List<User> findAll() {

        return userService.list();
        }
    @PostMapping("/login")
    public Result login(@RequestBody User user){

        User user1 = userService.login(user);

        return Result.success(user1);
    }


    @GetMapping("/{id}")
    public User findOne(@PathVariable String id) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_id",id);
        return userService.getOne(queryWrapper);
        }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                     @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");//降序排列
        return userService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}

