package com.example.repository.project_repo;

import com.example.domain.Programmer;
import com.example.domain.Project;
import com.example.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ProjectDbRepo implements ProjectRepo{
    private JdbcUtils dbUtils;
     public ProjectDbRepo(Properties props) {
         dbUtils = new JdbcUtils(props);
     }

    @Override
    public void add(Project elem) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into projects (name, deadline) values (?,?)")){
            preStm.setString(1, elem.getName());
            preStm.setString(2, elem.getDeadline());
            preStm.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Project elem) {

    }

    @Override
    public Iterable<Project> findAll() {
        return null;
    }

    @Override
    public Project findOne(String name) {
        Connection con = dbUtils.getConnection();
        Project project = null;
        try (PreparedStatement preStm = con.prepareStatement("SELECT * from projects where name = ?")) {
            preStm.setString(1, name);
            try (ResultSet result = preStm.executeQuery()) {
                int id = result.getInt("id");
                String deadline = result.getString("deadline");
                project = new Project(name, deadline);
                project.setId(id);
            }
        } catch (SQLException ignored) {}
        return project;
    }
}
