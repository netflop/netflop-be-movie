package com.netflop.be.movie.service;

import com.netflop.be.movie.dao.UserDao;
import com.netflop.be.movie.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserDao userDao;

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
