package com.example.repository.project_repo;

import com.example.domain.Programmer;
import com.example.domain.Project;
import com.example.repository.Repository;

public interface ProjectRepo extends Repository<Integer, Project> {
    Project findOne(String name);
}
