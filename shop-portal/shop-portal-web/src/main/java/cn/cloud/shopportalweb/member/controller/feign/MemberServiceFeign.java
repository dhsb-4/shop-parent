package cn.cloud.shopportalweb.member.controller.feign;

import cn.cloud.shopserviceapimember.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-service-member")
public interface MemberServiceFeign extends MemberService {

}
