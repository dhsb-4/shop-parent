package cn.cloud.shopservicemember.impl;

import cn.cloud.shopservicemember.fegin.VerificaCodeServiceFeign;
import org.springframework.web.bind.annotation.RestController;
import cn.cloud.shopserviceapimember.service.Huiyuan;

import javax.annotation.Resource;

/**
 * @program: shop-parent
 * <p>
 * 胖哥
 * @description:
 * @author: yueLi
 * @create: 2020-05-16 19:27
 **/
@RestController
public class HuiyuanImpl implements Huiyuan {

    @Resource
    VerificaCodeServiceFeign wxFegin;

    @Override
    public String getFingg() {

        return "成功"+wxFegin.getAll().getName();
    }
}
