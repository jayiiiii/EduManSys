package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IAreaCountService;
import com.example.springboot.entity.AreaCount;

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
@RequestMapping("/area-count")

public class AreaCountController {

    @Resource
    private IAreaCountService areaCountService;

    @PostMapping
    public Boolean save(@RequestBody AreaCount areaCount) {
        return areaCountService.saveOrUpdate(areaCount);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return areaCountService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return areaCountService.removeByIds(ids);
        }

    @GetMapping
    public List<AreaCount> findAll() {
        return areaCountService.list();
        }

    @GetMapping("/{id}")
    public AreaCount findOne(@PathVariable Integer id) {
        return areaCountService.getById(id);
        }

    @GetMapping("/page")
    public Page<AreaCount> findPage(@RequestParam Integer pageNum,
                     @RequestParam Integer pageSize) {
        QueryWrapper<AreaCount> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("wjy_sarea");//降序排列

        return areaCountService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}

