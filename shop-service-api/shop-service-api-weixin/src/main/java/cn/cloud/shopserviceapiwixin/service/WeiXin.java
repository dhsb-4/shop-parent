package cn.cloud.shopserviceapiwixin.service;


import entity.Wx;
import org.springframework.web.bind.annotation.GetMapping;

public interface WeiXin {

     @GetMapping("/wx")
     Wx getAll();
}
