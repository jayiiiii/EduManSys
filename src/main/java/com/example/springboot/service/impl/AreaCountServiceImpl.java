package com.example.springboot.service.impl;

import com.example.springboot.entity.AreaCount;
import com.example.springboot.mapper.AreaCountMapper;
import com.example.springboot.service.IAreaCountService;
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
public class AreaCountServiceImpl extends ServiceImpl<AreaCountMapper, AreaCount> implements IAreaCountService {

}
