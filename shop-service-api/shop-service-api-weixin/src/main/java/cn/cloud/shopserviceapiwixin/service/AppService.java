package cn.cloud.shopserviceapiwixin.service;


import entity.AppEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface AppService {

    @GetMapping("getApp")
    public AppEntity getApp();
}
