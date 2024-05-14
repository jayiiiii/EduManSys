package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.springboot.service.ICinfoViewService;
import com.example.springboot.entity.CinfoView;

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
@RequestMapping("/cinfo-view")
public class CinfoViewController {

    @Resource
    private ICinfoViewService cinfoViewService;

    @PostMapping
    public Boolean save(@RequestBody CinfoView cinfoView) {
        return cinfoViewService.saveOrUpdate(cinfoView);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return cinfoViewService.removeById(id);
        }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return cinfoViewService.removeByIds(ids);
        }

    @GetMapping
    public List<CinfoView> findAll() {
        return cinfoViewService.list();
        }

    @GetMapping("/{id}")
    public CinfoView findOne(@PathVariable Integer id) {
        return cinfoViewService.getById(id);
        }


    @GetMapping("/page")
    public Page<CinfoView> findPage(@RequestParam Integer pageNum,
                                    @RequestParam(defaultValue = "")Integer pageSize,
                                    @RequestParam(defaultValue = "") String wjyCno,
                                    @RequestParam (defaultValue = "")String wjyClno,
                                    @RequestParam(defaultValue = "")String wjyCterm
    ){//可以直接添加条件
        Page<CinfoView> page=new Page<>(pageNum,pageSize);
        QueryWrapper<CinfoView> queryWrapper = new QueryWrapper<>();
        if(!wjyCno.equals("")) {//一定要判断 不然这三个值为空的话则不显示
            queryWrapper.like("wjy_cno", wjyCno);
        }
        if(!wjyClno.equals("")){
            queryWrapper.like("wjy_clno",wjyClno);
        }
        if(!wjyCterm.equals("")){
            queryWrapper.like("wjy_cterm",wjyCterm);
        }

//        queryWrapper.or().like()

        return cinfoViewService.page(page,queryWrapper);
    }//分页查询数据
}

