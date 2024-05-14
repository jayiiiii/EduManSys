package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IDeptService;
import com.example.springboot.entity.Dept;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjy
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private IDeptService deptService;

    @PostMapping
    public Boolean save(@RequestBody Dept dept) {
        return deptService.saveOrUpdate(dept);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return deptService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return deptService.removeByIds(ids);
        }

    @GetMapping
    public List<Dept> findAll() {
        return deptService.list();
        }

    @GetMapping("/{id}")
    public Dept findOne(@PathVariable Integer id) {
        return deptService.getById(id);
        }

    @GetMapping("/page")
    public Page<Dept> findPage(@RequestParam Integer pageNum,
                     @RequestParam Integer pageSize) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("wjy_Dno ");//降序排列
        return deptService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}

