package com.example.repository.programmer_repo;

import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.repository.Repository;

public interface ProgrammerRepo extends Repository<Integer, Programmer> {
    Programmer findProgrammer(String username, String password);
}
