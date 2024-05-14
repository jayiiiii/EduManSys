package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IAvgScoreService;
import com.example.springboot.entity.AvgScore;

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

@RequestMapping("/avg-score")
public class AvgScoreController {

    @Resource
    private IAvgScoreService avgScoreService;

    @PostMapping
    public Boolean save(@RequestBody AvgScore avgScore) {
        return avgScoreService.saveOrUpdate(avgScore);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return avgScoreService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return avgScoreService.removeByIds(ids);
        }

    @GetMapping
    public List<AvgScore> findAll() {
        return avgScoreService.list();
        }

    @GetMapping("/{id}")
    public AvgScore findOne(@PathVariable Integer id) {
        return avgScoreService.getById(id);
        }


    @GetMapping("/page")
    public Page<AvgScore> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize) {
        QueryWrapper<AvgScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("wjy_cname");//降序排列
        return avgScoreService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }
}

