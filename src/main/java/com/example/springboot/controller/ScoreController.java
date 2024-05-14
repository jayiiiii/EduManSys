package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IScoreService;
import com.example.springboot.entity.Score;

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
@RequestMapping("/score")
public class ScoreController {

    @Resource
    private IScoreService scoreService;

    @PostMapping
    public Boolean save(@RequestBody Score score) {
        QueryWrapper<Score>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_sno",score.getWjySno());
        queryWrapper.eq("wjy_cno",score.getWjyCno());
        return scoreService.update(score,queryWrapper);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return scoreService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return scoreService.removeByIds(ids);
        }

    @GetMapping
    public List<Score> findAll() {
        return scoreService.list();
        }

    @GetMapping("/{id}")
    public Score findOne(@PathVariable Integer id) {
        return scoreService.getById(id);
        }

    @GetMapping("/page")
    public Page<Score> findPage(@RequestParam Integer pageNum,
                     @RequestParam Integer pageSize) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");//降序排列
        return scoreService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}

