package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IClassService;
import com.example.springboot.entity.Class;

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
@RequestMapping("/class")
public class ClassController {

    @Resource
    private IClassService classService;

    @PostMapping
    public Boolean save(@RequestBody Class class1) {
        return classService.saveOrUpdate(class1);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return classService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return classService.removeByIds(ids);
        }

    @GetMapping
    public List<Class> findAll() {
        return classService.list();
        }

    @GetMapping("/{id}")
    public Class findOne(@PathVariable Integer id) {
        return classService.getById(id);
        }

    @GetMapping("/page")
    public Page<Class> findPage(@RequestParam Integer pageNum,
                     @RequestParam Integer pageSize) {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("wjy_clno");//降序排列
        return classService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}

