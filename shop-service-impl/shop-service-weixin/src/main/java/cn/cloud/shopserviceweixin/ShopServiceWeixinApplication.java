package cn.cloud.shopserviceweixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: shop-parent
 * <p>
 * 胖哥
 * @description: 服务者
 * @author: yueLi
 * @create: 2020-04-02 11:37
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class
                        , HibernateJpaAutoConfiguration.class})
@ComponentScan("cn.cloud")
@EnableEurekaClient
@EnableSwagger2
@Configuration
@EnableFeignClients
public class ShopServiceWeixinApplication {
    public static void main(String[] args) {
        SpringApplication. run(ShopServiceWeixinApplication.class,args);
    }
}
