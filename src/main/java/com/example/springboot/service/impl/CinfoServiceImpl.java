package com.example.springboot.service.impl;

import com.example.springboot.entity.Cinfo;
import com.example.springboot.mapper.CinfoMapper;
import com.example.springboot.service.ICinfoService;
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
public class CinfoServiceImpl extends ServiceImpl<CinfoMapper, Cinfo> implements ICinfoService {

}
