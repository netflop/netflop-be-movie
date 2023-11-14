package com.netflop.be.movie.dao;


public interface IDao<T> {
    T insert(T t);
    T findById(String id);
    T delete(String id);
}
