package com.example.springboot.service.impl;

import com.example.springboot.entity.CinfoView;
import com.example.springboot.mapper.CinfoViewMapper;
import com.example.springboot.service.ICinfoViewService;
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
public class CinfoViewServiceImpl extends ServiceImpl<CinfoViewMapper, CinfoView> implements ICinfoViewService {

}
