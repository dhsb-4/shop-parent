package cn.cloud.shopbasicsspringcloudzuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableSwagger2
public class ShopBasicsSpringcloudZuulApplication {
   public static void main(String[] args) {
        SpringApplication.run(ShopBasicsSpringcloudZuulApplication.class,args);
    }


    @Autowired
    private RouteLocator routeLocator;

    @Component
    @Primary
    class  DocumentationConfig implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List resources =new ArrayList<>();
            List<Route> routes = routeLocator.getRoutes();
            for (Route route: routes) {
                resources.add(swaggerResource(route.getLocation(),"/"+route.getId()+"/v2/api-docs","2.0"));
            }
            return resources;
        }

        private SwaggerResource swaggerResource(String name,String location,String version){
            SwaggerResource swaggerResource=new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }
}
