package com.example.repository;

public interface Repository<ID, T> {
    void add(T elem);
    void update(T elem);
    Iterable<T> findAll();
}
