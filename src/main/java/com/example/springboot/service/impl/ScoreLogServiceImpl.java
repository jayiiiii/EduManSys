package com.example.springboot.service.impl;

import com.example.springboot.entity.ScoreLog;
import com.example.springboot.mapper.ScoreLogMapper;
import com.example.springboot.service.IScoreLogService;
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
public class ScoreLogServiceImpl extends ServiceImpl<ScoreLogMapper, ScoreLog> implements IScoreLogService {

}
