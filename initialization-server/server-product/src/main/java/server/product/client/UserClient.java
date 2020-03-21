package server.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.commom.dto.User;
import server.product.client.hystrix.UserClientHystrix;

/**
 * @author tieMinPan
 * @Desc: 用户feign 访问 用户微服务
 * @date 2020-03-13 11:29
 */
@FeignClient(value = "SERVER-USER",fallback = UserClientHystrix.class)
public interface UserClient {

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @GetMapping(value = "/user/getById/{id}")
    User getById(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/user/accessAdd")
    boolean accessAdd(@RequestParam Integer userId, @RequestParam String name);
}
