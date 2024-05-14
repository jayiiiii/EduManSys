package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Course;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.ICinfoService;
import com.example.springboot.entity.Cinfo;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/cinfo")
public class CinfoController {

    @Resource
    private ICinfoService cinfoService;

    @PostMapping
    public Boolean save(@RequestBody Cinfo cinfo) {

        QueryWrapper<Cinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wjy_cno", cinfo.getWjyCno());
        queryWrapper.eq("wjy_clno", cinfo.getWjyClno());
        try {
            return cinfoService.save(cinfo);
        } catch (Exception e) {
            return false;
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return cinfoService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return cinfoService.removeByIds(ids);
    }

    @GetMapping
    public List<Cinfo> findAll() {
        return cinfoService.list();
    }

    @GetMapping("/{id}")
    public Cinfo findOne(@PathVariable Integer id) {
        return cinfoService.getById(id);
    }

    @GetMapping("/page")
    public Page<Cinfo> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Cinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("wjy_cno");//降序排列
        return cinfoService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}

