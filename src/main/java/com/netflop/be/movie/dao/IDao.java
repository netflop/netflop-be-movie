package com.netflop.be.movie.dao;

import java.util.List;

public interface IDao<T> {
    T save(T t);
    T findById(String id);
}
