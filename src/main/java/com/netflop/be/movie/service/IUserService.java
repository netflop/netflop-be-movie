package com.netflop.be.movie.service;

import com.netflop.be.movie.entity.User;

public interface IUserService {
    User findUserByEmail(String email);
}
