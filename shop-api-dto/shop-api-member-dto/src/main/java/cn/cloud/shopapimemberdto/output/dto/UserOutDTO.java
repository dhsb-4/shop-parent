package cn.cloud.shopapimemberdto.output.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * 
 * @description: 响应返回参数
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@ApiModel(value = "用户返回参数")
public class UserOutDTO {

	/**
	 * userid
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String mobile;
	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;
	/**
	 * 性别 0 男 1女
	 */
	@ApiModelProperty(value = "用户性别")
	private char sex;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "用户年龄")
	private Integer age;
	/**
	 * 注册时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 修改时间
	 *
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**
	 * 账号是否可以用 1 正常 0冻结
	 */
	@ApiModelProperty(value = "账号是否可以用 1 正常 0冻结")
	private char isAvalible;
	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = " 用户头像")
	private String picImg;
	/**
	 * 用户关联 QQ 开放ID
	 */
	@ApiModelProperty(value = "用户关联 QQ 开放ID")
	private String qqOpenId;
	/**
	 * 用户关联 微信 开放ID
	 */
	@ApiModelProperty(value = "用户关联 微信 开放ID")
	private String wxOpenId;

	private Boolean isVip;

	// 目前存在的问题： 接口层传递实体类→数据库传递实体类 都是相同的。
	// 注册的时候不需要传递isAvalible updateTime createTime
	// 查询的用户相关信息的时候，是不需要将密码返回给客户端
	// 请求参数与返回参数如果公用同一个实体类会存在那些问题？ 可能会暴露数据库字段攻击 安全性
	// 修改用户密码

	// DTO 主要用于外部接口参数传递封装 接口与接口进行传递使用
	// DO主要操作用于数据库层传递
	// VO主要用于视图层展示
	// DTO转换DO (接口接受参数，转换为数据库实体类插入数据库中) DO转换DTO（向数据库查询数据转换DTO返回给客户端）


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public char getIsAvalible() {
		return isAvalible;
	}

	public void setIsAvalible(char isAvalible) {
		this.isAvalible = isAvalible;
	}

	public String getPicImg() {
		return picImg;
	}

	public void setPicImg(String picImg) {
		this.picImg = picImg;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public Boolean getVip() {
		return isVip;
	}

	public void setVip(Boolean vip) {
		isVip = vip;
	}
}
