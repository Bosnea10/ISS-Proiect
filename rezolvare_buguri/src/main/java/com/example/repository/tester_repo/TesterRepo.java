package com.example.repository.tester_repo;

import com.example.domain.Tester;
import com.example.repository.Repository;

public interface TesterRepo extends Repository<Integer, Tester> {
    Tester findTester(String username, String password);
}
