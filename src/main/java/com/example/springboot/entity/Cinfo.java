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
  @TableName("wujy_cinfo")
@ApiModel(value = "Cinfo对象", description = "")
public class Cinfo implements Serializable {

    private static final long serialVersionUID = 1L;

      private String wjyCno;

    private String wjyTno;

    private String wjyClno;


}
