package server.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tieMinPan
 * @Desc: 用户服务
 * @date 2020-03-13 10:05
 */
@EnableFeignClients
@MapperScan(value = "server.user.dao")
@EnableSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }
}
