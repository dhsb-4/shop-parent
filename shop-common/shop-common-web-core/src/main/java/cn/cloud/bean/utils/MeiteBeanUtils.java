package cn.cloud.bean.utils;

import org.springframework.beans.BeanUtils;

/**
 * 
 * 
 * 
 * @description:Vo转换为DTO 或者Dto转换为Vo
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
public class MeiteBeanUtils<Vo, Dto> {

	/**
	 * dot 转换为Do 工具类
	 * 
	 * @param dtoEntity
	 * @param doEntity
	 * @return
	 */
	public static <Dto> Dto voToDto(Object voEntity, Class<Dto> dtoClass) {
		// 判断VoSF是否为空!
		if (voEntity == null) {
			return null;
		}
		// 判断DtoClass 是否为空
		if (dtoClass == null) {
			return null;
		}
		try {
			Dto newInstance = dtoClass.newInstance();
			BeanUtils.copyProperties(voEntity, newInstance);
			// Dto转换Do
			return newInstance;
		} catch (Exception e) {
			return null;
		}
	}

	// 后面集合类型带封装
}
