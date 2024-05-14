package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.ICourseService;
import com.example.springboot.entity.Course;

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
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @PostMapping
    public Boolean save(@RequestBody Course course) {
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("wjy_cno",course.getWjyCno());
        return courseService.save(course);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return courseService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return courseService.removeByIds(ids);
        }

    @GetMapping
    public List<Course> findAll() {
        return courseService.list();
        }

    @GetMapping("/{cno}")
    public Course findOne(@PathVariable String cno) {
        QueryWrapper<Course>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_cno",cno);
        return courseService.getOne(queryWrapper);
    }
    @GetMapping("/page")
    public Page<Course> findPage(@RequestParam Integer pageNum,
                     @RequestParam Integer pageSize) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("wjy_cno");//降序排列
        return courseService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}

