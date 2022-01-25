package com.tsy.blog.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ms_admin_role")
@ApiModel(value="AdminRole对象", description="")
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer adminId;

    private Integer roleId;


}
