package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Teacher;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.IScoreLogService;
import com.example.springboot.entity.ScoreLog;

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
@RequestMapping("/score-log")
public class ScoreLogController {

    @Resource
    private IScoreLogService scoreLogService;

    @PostMapping
    public Boolean save(@RequestBody ScoreLog scoreLog) {

        QueryWrapper<ScoreLog>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_sno",scoreLog.getWjySno());
        queryWrapper.eq("wjy_cno",scoreLog.getWjyCno());
        return scoreLogService.update(scoreLog,queryWrapper)||save(scoreLog);

    }
    @GetMapping("/orderBy")
    public List<ScoreLog> orderBy(){
        QueryWrapper<ScoreLog> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("wjy_cno").orderByDesc("wjy_score");
        return scoreLogService.list(queryWrapper);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return scoreLogService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return scoreLogService.removeByIds(ids);
        }

    @GetMapping
    public List<ScoreLog> findAll() {
        return scoreLogService.list();
        }

    @GetMapping("/{id}")
    public ScoreLog findOne(@PathVariable Integer id) {
        return scoreLogService.getById(id);
        }

    @GetMapping("/page")
    public Page<ScoreLog> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam String wjyCno,
                                   @RequestParam String wjyClno,
                                   @RequestParam String wjyCterm,
                                   @RequestParam String wjyTno,
                                   @RequestParam String wjySno
                                   ) {
        Page<ScoreLog> page=new Page<>(pageNum,pageSize);
        QueryWrapper<ScoreLog> queryWrapper = new QueryWrapper<>();
        if(!wjyCno.equals("")) {
            queryWrapper.like("wjy_cno", wjyCno);
        }
        if(!wjyClno.equals("")){
            queryWrapper.eq("wjy_clno",wjyClno);
        }
        if(!wjyCterm.equals("")){
            queryWrapper.like("wjy_cterm",wjyCterm);
        }
        if(!wjyTno.equals("")){
            queryWrapper.like("wjy_tno",wjyTno);
        }
        if(!wjySno.equals("")){
            queryWrapper.like("wjy_sno",wjySno);
        }
//        queryWrapper.or().like()
       queryWrapper.orderByAsc("wjy_sno");

        return scoreLogService.page(page,queryWrapper);
}

    @GetMapping("/non")
    public Page<ScoreLog> findnon(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam String wjyCno,
                                   @RequestParam String wjyClno,
                                   @RequestParam String wjyCterm,
                                   @RequestParam String wjyTno,
                                   @RequestParam String wjySno,@RequestParam String check
                                  ) {
        Page<ScoreLog> page=new Page<>(pageNum,pageSize);
        QueryWrapper<ScoreLog> queryWrapper = new QueryWrapper<>();
        if(!wjyCno.equals("")) {
            queryWrapper.like("wjy_cno", wjyCno);
        }
        if(!wjyClno.equals("")){
            queryWrapper.eq("wjy_clno",wjyClno);
        }
        if(!wjyCterm.equals("")){
            queryWrapper.like("wjy_cterm",wjyCterm);
        }
        if(!wjyTno.equals("")){
            queryWrapper.like("wjy_tno",wjyTno);
        }
        if(!wjySno.equals("")){
            queryWrapper.like("wjy_sno",wjySno);
        }
//        queryWrapper.or().like()
        queryWrapper.orderByAsc("wjy_sno");
        if(check.equals("1")) {
            queryWrapper.isNull("wjy_score");
        }
        return scoreLogService.page(page,queryWrapper);
    }
}

