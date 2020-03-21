package server.product.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.product.client.UserClient;
import server.product.dao.ProductMapper;
import server.product.model.Product;
import server.product.service.IBuyService;

/**
 * @author tieMinPan
 * @Desc: Description
 * @date 2020-03-16 16:38
 */
@Service
public class BuyServiceImpl implements IBuyService {

    @Autowired
    private UserClient userClient;


    @Autowired
    private ProductMapper productMapper;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void add(Integer id, String name) {
        userClient.accessAdd(id, name);
        productMapper.insert(new Product(id, name));
        return;
    }
}
