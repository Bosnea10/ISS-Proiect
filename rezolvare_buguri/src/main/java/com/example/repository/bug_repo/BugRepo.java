package com.example.repository.bug_repo;

import com.example.domain.Bug;
import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.repository.Repository;

import java.util.List;

public interface BugRepo extends Repository<Integer, Bug> {
    void updateProgrammer(Bug bug);
    void updateUnresolved(Bug bug);
    void delete(Bug bug);
    List<Bug> findAllAssigned(Programmer programmer);
}
