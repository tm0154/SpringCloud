package cn.tmpan.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author tieMinPan
 * @Desc:
 * EnableZuulProxy: 标识当前的应用是 Zuul Server
 * SpringCloudApplication: 用于简化配置的组合注解
 * @date 2020-03-11 17:48
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
