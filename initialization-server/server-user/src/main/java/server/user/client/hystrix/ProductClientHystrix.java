package server.user.client.hystrix;

import org.springframework.stereotype.Component;
import server.commom.dto.Product;
import server.user.client.ProductClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tieMinPan
 * @Desc: 产品微服务熔断返回
 * @date 2020-03-13 12:01
 */
@Component
public class ProductClientHystrix implements ProductClient {
    /**
     * 通过获取产品信息
     *
     * @param id
     * @return
     */
    @Override
    public Product getProductById(Integer id) {

        return new Product(0, "产品服务熔断!");
    }

    /**
     * 获取产品列表
     *
     * @param num
     * @param name
     * @return
     */
    @Override
    public List<Product> getLists(Integer num, String name) {

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(0, "产品服务熔断返回结果"));
        return productList;
    }
}
