package com.example.springboot.service.impl;

import com.example.springboot.entity.AvgScore;
import com.example.springboot.mapper.AvgScoreMapper;
import com.example.springboot.service.IAvgScoreService;
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
public class AvgScoreServiceImpl extends ServiceImpl<AvgScoreMapper, AvgScore> implements IAvgScoreService {

}
