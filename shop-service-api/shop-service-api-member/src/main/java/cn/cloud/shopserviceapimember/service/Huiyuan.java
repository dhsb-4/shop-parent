package cn.cloud.shopserviceapimember.service;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: shop-parent
 * <p>
 * 胖哥
 * @description:
 * @author: yueLi
 * @create: 2020-05-16 17:03
 **/

public interface Huiyuan {
    @GetMapping("c")
    String getFingg();
}
