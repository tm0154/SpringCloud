package server.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import server.commom.dto.Product;
import server.user.client.hystrix.ProductClientHystrix;

import java.util.List;

/**
 * @author tieMinPan
 * @Desc: 通过feign 调用产品微服务
 * @date 2020-03-13 11:50
 */
@FeignClient(value = "SERVER-PRODUCT", fallback = ProductClientHystrix.class)
public interface ProductClient {

    /**
     * 通过获取产品信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/product/getById/{id}")
    Product getProductById(@PathVariable(value = "id") Integer id);

    /**
     * 获取产品列表( 多个参数需要@RequestParam注解,只能有一个@RequestBody)
     *
     * @param num
     * @param name
     * @return
     */
    @PostMapping(value = "product/getLists")
    List<Product> getLists(@RequestParam Integer num, @RequestParam String name);
}
