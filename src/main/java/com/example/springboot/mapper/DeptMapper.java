package com.example.springboot.mapper;

import com.example.springboot.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjy
 * @since 2023-07-01
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

}
