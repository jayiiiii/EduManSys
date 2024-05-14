package com.example.springboot.mapper;

import com.example.springboot.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjy
 * @since 2023-06-28
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

}
