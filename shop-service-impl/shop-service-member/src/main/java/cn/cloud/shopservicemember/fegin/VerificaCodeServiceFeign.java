package cn.cloud.shopservicemember.fegin;

import cn.cloud.shopserviceapiwixin.service.VerificaCodeService;
import entity.Wx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("app-service-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {

    @GetMapping("/wx")
    Wx getAll();
}
