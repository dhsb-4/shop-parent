package cn.cloud.shopserviceweixin.impl;

import cn.cloud.shopserviceapiwixin.service.AppService;
import entity.AppEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppServiceImpl implements AppService {

    @Override
    @GetMapping("/getDh")
    public AppEntity getApp() {
        return new AppEntity("21345","xhzshisb");
    }
}
