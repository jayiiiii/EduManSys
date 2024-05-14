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
  @TableName("wujy_score")
@ApiModel(value = "Score对象", description = "")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

      private String wjySno;

    private String wjyCno;

    private Integer wjyScore;


}
