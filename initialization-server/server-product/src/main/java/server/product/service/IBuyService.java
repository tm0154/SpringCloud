package server.product.service;

import io.seata.spring.annotation.GlobalTransactional;

/**
 * @author tieMinPan
 * @Desc: Description
 * @date 2020-03-16 16:38
 */
public interface IBuyService {



    void add(Integer id, String name);
}
