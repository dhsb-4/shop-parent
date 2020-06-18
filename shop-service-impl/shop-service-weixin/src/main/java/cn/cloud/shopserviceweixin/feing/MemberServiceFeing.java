package cn.cloud.shopserviceweixin.feing;

import cn.cloud.shopserviceapimember.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-service-member")
public interface MemberServiceFeing extends MemberService {

}
