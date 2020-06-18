package cn.cloud.shopportalweb.member.controller.req.vo;


public class LoginVo {

	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 手机密码
	 */
	private String password;
	/**
	 * 图形验证码
	 */
	private String graphicCode;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGraphicCode() {
		return graphicCode;
	}

	public void setGraphicCode(String graphicCode) {
		this.graphicCode = graphicCode;
	}
}