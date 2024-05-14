package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.User;
import java.net.URLEncoder;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

import com.example.springboot.service.ITeacherService;
import com.example.springboot.entity.Teacher;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjy
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private ITeacherService teacherService;

    @PostMapping
    public Boolean save(@RequestBody Teacher teacher) {

        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_tno",teacher.getWjyTno());
        try{
        return teacherService.update(teacher,queryWrapper)||teacherService.save(teacher);
    }
        catch(Exception e){
        return false;
    }
    }
    @DeleteMapping("/{tno}")
    public Boolean delete(@PathVariable String tno) {
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        wrapper.eq("wjy_tno",tno);
        return teacherService.remove(wrapper);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<String> tnos){
        for(int i = 0;i < tnos.size()-1;i++){
            if(! delete(tnos.get(i))) return false;
        }
        return delete(tnos.get(tnos.size()-1));
    }

    @GetMapping
    public List<Teacher> findAll() {
        return teacherService.list();
        }

    @GetMapping("/{tno}")
    public Teacher findOne(@PathVariable String tno) {
        QueryWrapper<Teacher>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_tno",tno);
        return teacherService.getOne(queryWrapper);
    }

    @GetMapping("/page")
    public Page<Teacher> findPage(@RequestParam Integer pageNum,
                                  @RequestParam(defaultValue = "")Integer pageSize,
                                  @RequestParam(defaultValue = "") String wjyTno,
                                  @RequestParam (defaultValue = "")String wjyTposition,
                                  @RequestParam(defaultValue = "")String wjyTname
    ){//可以直接添加条件
        Page<Teacher> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(!wjyTno.equals("")) {//一定要判断 不然这三个值为空的话则不显示
            queryWrapper.like("wjy_tno", wjyTno);
        }
        if(!wjyTposition.equals("")){
            queryWrapper.like("wjy_tposition",wjyTposition);
        }
        if(!wjyTname.equals("")){
            queryWrapper.like("wjy_tname",wjyTname);
        }

//        queryWrapper.or().like()
        queryWrapper.orderByAsc("wjy_tno");
        return teacherService.page(page,queryWrapper);
    }//分页查询数据

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Teacher> list = teacherService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("wjy_tno", "教师编号");
        writer.addHeaderAlias("wjy_tname", "教师姓名");
        writer.addHeaderAlias("wjy_tsex", "班级编号");
        writer.addHeaderAlias("wjy_tage", "年龄");
        writer.addHeaderAlias("wjy_tposition", "职位");
        writer.addHeaderAlias("wjy_tnumber", "联系方式");
        writer.addHeaderAlias("wjy_tpassword", "密码");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }
    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Teacher> teachers = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Teacher teacher=new Teacher();
            teacher.setWjyTno(row.get(0).toString());
            teacher.setWjyTname(row.get(1).toString());
            teacher.setWjyTsex(row.get(2).toString());
            teacher.setWjyTage(Integer.parseInt(row.get(3).toString()));
            teacher.setWjyTposition(row.get(4).toString());
            teacher.setWjyTposition(row.get(5).toString());
            teachers.add(teacher);
        }

        teacherService.saveBatch(teachers);
        return true;
    }
}

