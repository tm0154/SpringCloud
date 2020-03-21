package server.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.user.dao.UserMapper;
import server.user.model.User;
import server.user.service.IBuyServices;

/**
 * @author tieMinPan
 * @Desc: Description
 * @date 2020-03-16 16:41
 */
@Service
public class BuyServiceImpl implements IBuyServices {

    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accessAdd(Integer userId, String str) {

        int insert = userMapper.insert(new User(userId, str));
        if (insert > 0) {
            return true;
        }
       throw  new RuntimeException("errors");
    }
}
