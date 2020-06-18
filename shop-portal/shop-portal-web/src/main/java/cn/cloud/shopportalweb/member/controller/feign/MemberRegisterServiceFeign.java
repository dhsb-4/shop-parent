package cn.cloud.shopportalweb.member.controller.feign;

import cn.cloud.shopserviceapimember.service.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-service-member")
public interface MemberRegisterServiceFeign extends MemberRegisterService {
}
