package server.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.commom.dto.Product;
import server.user.client.ProductClient;
import server.user.model.User;

import java.util.List;

/**
 * @author tieMinPan
 * @Desc: 用户模块
 * @date 2020-03-13 10:36
 */
@RequestMapping(value = "user")
@RestController
public class UserController {


    @Autowired
    private ProductClient productClient;

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "getById/{id}")
    public User getById(@PathVariable(value = "id") Integer id) {
        return new User(id, "test-" + id);
    }

    /**
     * 获取远程产品微服务数据
     *
     * @param num
     * @param name
     * @return
     */
    @PostMapping(value = "getProductServerList")
    public List<Product> getProductServerList(Integer num, String name) {
        return productClient.getLists(num, name);
    }


    /**
     * 获取微服务调用
     * @param id
     * @return
     */
    @GetMapping(value = "getProductServiceById/{id}")
    public Product getProductServiceById(@PathVariable(value = "id") Integer id) {
        return productClient.getProductById(id);
    }

}
