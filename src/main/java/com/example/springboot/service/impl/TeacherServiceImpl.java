package com.example.springboot.service.impl;

import com.example.springboot.entity.Teacher;
import com.example.springboot.mapper.TeacherMapper;
import com.example.springboot.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjy
 * @since 2023-06-28
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

}
