package cn.cloud.shopportalweb.member.controller.feign;

import cn.cloud.shopserviceapimember.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-service-member")
public interface MemberLoginServiceFeign extends MemberLoginService {
}
