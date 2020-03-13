package server.product.client.hystrix;

import org.springframework.stereotype.Component;
import server.commom.dto.User;
import server.product.client.UserClient;

/**
 * @author tieMinPan
 * @Desc: Description
 * @date 2020-03-13 11:44
 */
@Component
public class UserClientHystrix implements UserClient {
    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @Override
    public User getById(Integer id) {
        return new User(0, "用户熔断,暂无该用户");
    }
}
