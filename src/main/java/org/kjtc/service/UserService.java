package org.kjtc.service;

import org.kjtc.entity.User;
import org.kjtc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserList(User user) {
        return userMapper.getUserList(user);
    }

    public User getUser(User user) {
        return userMapper.getUser(user);
    }

    public int saveEquipment(User user) {
        return userMapper.saveUser(user);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int updatePassword(User user) {
        return userMapper.updatePassword(user);
    }
}
