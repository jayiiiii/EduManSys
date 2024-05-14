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
 * @since 2023-07-01
 */
@Getter
@Setter
  @TableName("wujy_dept")
@ApiModel(value = "Dept对象", description = "")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

      private String wjyDno;

    private String wjyDept;


}
