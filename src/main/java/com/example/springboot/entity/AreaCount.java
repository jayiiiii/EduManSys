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
  @TableName("wujy_area_count")
@ApiModel(value = "AreaCount对象", description = "")
public class AreaCount implements Serializable {

    private static final long serialVersionUID = 1L;
      private String wjySarea;
    private Integer wjyCount;


}
