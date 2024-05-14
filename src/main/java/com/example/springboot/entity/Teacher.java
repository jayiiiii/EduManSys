package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-06-28
 */
@Getter
@Setter
  @TableName("wujy_teacher")
@ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

      private String wjyTno;

    private String wjyTname;

    private String wjyTsex;

    private Integer wjyTage;

      private String wjyTposition;

    private String wjyTnumber;

    private String wjyTpassword;


}
