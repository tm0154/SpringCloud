package server.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.commom.dto.User;
import server.product.client.UserClient;
import server.product.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tieMinPan
 * @Desc: 产品模块
 * @date 2020-03-13 10:36
 */
@RequestMapping(value = "product")
@RestController
public class ProductController {

    @Autowired
    private UserClient userClient;

    /**
     * 获取产品信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "getById/{id}")
    public Product getById(@PathVariable(value = "id") Integer id) {
        return new Product(id, "test-product-" + id);
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "getUserServerById")
    public User getUserServerById(Integer id) {
        return userClient.getById(id);
    }


    /**
     * 获取产品列表
     *
     * @param num
     * @param name
     * @return
     */
    @PostMapping(value = "getLists")
    public List<Product> getLists(Integer num, String name) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            productList.add(new Product(i, "产品名称-" + name + "-" + i));
        }
        return productList;
    }
}
