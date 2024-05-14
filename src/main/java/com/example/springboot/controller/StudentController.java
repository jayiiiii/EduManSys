package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.Student;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.springboot.service.IStudentService;
import com.example.springboot.entity.Student;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    @PostMapping
    public Boolean save(@RequestBody Student student) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();

        queryWrapper.like("wjy_sno",student.getWjySno());

        try{
            return studentService.update(student, queryWrapper) || studentService.save(student);
        }
        catch(Exception e){
            return false;
        }

    }

    @DeleteMapping("/{sno}")
    public Boolean delete(@PathVariable String sno) {

        QueryWrapper<Student> wrapper=new QueryWrapper<>();
        wrapper.eq("wjy_sno",sno);
        return studentService.remove(wrapper);
        }
        
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<String> snos){

        for(int i = 0;i < snos.size()-1;i++){
            if(! delete(snos.get(i))) return false;
        }
        return delete(snos.get(snos.size()-1));
        }

    @GetMapping
    public List<Student> findAll() {
        return studentService.list();
        }

    @GetMapping("/{sno}")
    public Student findOne(@PathVariable String sno) {
        QueryWrapper<Student>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("wjy_sno",sno);
        return studentService.getOne(queryWrapper);
    }

    @GetMapping("/page")
    public Page<Student> findPage(@RequestParam Integer pageNum,
                                  @RequestParam(defaultValue = "")Integer pageSize,
                                  @RequestParam(defaultValue = "") String wjySno,
                                  @RequestParam (defaultValue = "")String wjyClno,
                                  @RequestParam(defaultValue = "")String wjyDept
    ){//可以直接添加条件
        Page<Student> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(!wjySno.equals("")) {//一定要判断 不然这三个值为空的话则不显示
            queryWrapper.like("wjy_sno", wjySno);
        }
        if(!wjyClno.equals("")){
            queryWrapper.like("wjy_clno",wjyClno);
        }
        if(!wjyDept.equals("")){
            queryWrapper.like("wjy_dept",wjyDept);
        }
queryWrapper.orderByAsc("wjy_sno");
//        queryWrapper.or().like()
        return studentService.page(page,queryWrapper);
    }//分页查询数据



    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Student> list = studentService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("wjySno", "学号");
        writer.addHeaderAlias("wjySname", "学生姓名");
        writer.addHeaderAlias("wjyClno", "班级编号");
        writer.addHeaderAlias("wjyDept", "专业编号");
        writer.addHeaderAlias("wjySsex", "性别");
        writer.addHeaderAlias("wjySage", "年龄");
        writer.addHeaderAlias("wjySarea", "地区");
        writer.addHeaderAlias("wjyScredits", "总学分");
        writer.addHeaderAlias("wjySpassword", "密码");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生信息", "UTF-8");
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
        List<Student> students = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Student student=new Student();
            student.setWjySno(row.get(0).toString());
            student.setWjySname(row.get(1).toString());
            student.setWjyClno(row.get(2).toString());
            student.setWjyDept(row.get(3).toString());
            student.setWjySsex(row.get(4).toString());
            student.setWjySage(Integer.parseInt(row.get(5).toString()));
            student.setWjySarea(row.get(6).toString());
            student.setWjyScredits(Integer.parseInt(row.get(7).toString()));
            student.setWjySpassword(row.get(8).toString());
            students.add(student);
        }

        studentService.saveBatch(students);
        return true;
    }
}

