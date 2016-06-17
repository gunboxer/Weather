package com.weather.web.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.weather.web.domain.User;
import com.weather.web.mappers.UserMapper;
import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ryagudin
 */
@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private SqlSession sqlSession;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUserByName(String userName) throws FileNotFoundException {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByName(userName);
        logger.info("getUserByName requested userName is " + userName + (user == null ? " which is not exists in DB." : " and it's ok."));
        return user;
    }
}
