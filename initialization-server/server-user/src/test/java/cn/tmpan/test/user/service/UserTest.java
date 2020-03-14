package cn.tmpan.test.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import server.user.UserServerApplication;
import server.user.dao.UserMapper;
import server.user.model.User;

import java.util.List;

/**
 * @author tieMinPan
 * @Desc: Description
 * @date 2020-03-14 10:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServerApplication.class})
@Slf4j
public class UserTest {


    @Autowired
    private UserMapper userMapper;


    @Test
    public void test() {
        final List<User> users = userMapper.selectList(new QueryWrapper<>());
        log.info("user:{}",users);
    }

}
