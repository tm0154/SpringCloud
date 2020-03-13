package server.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tieMinPan
 * @Desc: 产品服务
 * @date 2020-03-13 11:16
 */
@EnableFeignClients
@MapperScan(value = "server.product.dao")
@EnableSwagger2
@SpringBootApplication
public class ProductServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServerApplication.class, args);
    }
}
