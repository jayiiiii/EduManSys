package com.example.springboot.service.impl;

import com.example.springboot.entity.Class;
import com.example.springboot.mapper.ClassMapper;
import com.example.springboot.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

}
