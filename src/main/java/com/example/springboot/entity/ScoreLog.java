package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjy
 * @since 2023-06-27
 */
@Getter
@Setter
  @TableName("wujy_score_log")
@ApiModel(value = "ScoreLog对象", description = "")
public class ScoreLog implements Serializable {

    private static final long serialVersionUID = 1L;

      private String wjyCno;

    private String wjyCname;

    private String wjyCterm;

    private String wjyTno;

      private String wjyClno;

    private String wjySno;

    private String wjySname;

    private Integer wjyScore;


}
