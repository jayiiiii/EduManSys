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
  @TableName("wujy_student")
@ApiModel(value = "Student对象", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

      private String wjySno;

    private String wjySname;

    private String wjyClno;

    private String wjyDept;

      private String wjySsex;

    private Integer wjySage;

    private String wjySarea;

    private Integer wjyScredits;

    private String wjySpassword;


}
