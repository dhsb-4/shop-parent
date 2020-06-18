package cn.cloud.shopserviceweixin.impl;

import entity.Wx;
import org.springframework.web.bind.annotation.RestController;
import cn.cloud.shopserviceapiwixin.service.WeiXin;

/**
 * @program: shop-parent
 * <p>
 * 胖哥
 * @description:
 * @author: yueLi
 * @create: 2020-05-14 20:35
 **/
@RestController
public class WeiXinImpl implements WeiXin {

    @Override

    public Wx getAll() {
        return new Wx("大海","1235");
    }
}
