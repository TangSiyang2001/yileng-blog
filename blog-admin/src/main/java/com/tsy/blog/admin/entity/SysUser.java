package com.tsy.blog.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tsy.blog.admin.web.serialize.DateTimeFormatSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode()
@TableName("ms_sys_user")
@ApiModel(value="SysUser对象")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "账户不能为空")
    @ApiModelProperty(value = "账号")
    private String account;

    @NotBlank(message = "管理员状态不能为空")
    @ApiModelProperty(value = "是否管理员")
    private Boolean admin;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @JsonSerialize(using = DateTimeFormatSerializer.class)
    @ApiModelProperty(value = "注册时间")
    private Long createDate;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "最后登录时间")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Long lastLogin;

    @Length(max = 11,min = 11,message = "手机号长度不匹配")
    @ApiModelProperty(value = "手机号")
    private String mobilePhoneNumber;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "加密盐")
    private String salt;

    @ApiModelProperty(value = "状态")
    private String status;
}
