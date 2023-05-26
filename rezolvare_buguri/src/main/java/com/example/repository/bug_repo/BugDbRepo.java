package com.example.repository.bug_repo;

import com.example.domain.Bug;
import com.example.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BugDbRepo implements BugRepo {
    private JdbcUtils dbUtils;

    public BugDbRepo(Properties props) {
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Bug elem) {

    }

    @Override
    public void update(Integer integer, Bug elem) {

    }

    @Override
    public Iterable<Bug> findAll() {
        Connection con= dbUtils.getConnection();
        List<Bug> bugs=new ArrayList<>();
        try(PreparedStatement preStm=con.prepareStatement("select * from bugs where programmer_name is null")){
            try(ResultSet result=preStm.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String projectId = result.getString("project_name");
                    String programmerId = result.getString("programmer_name");
                    Bug bug=new Bug(name, description, projectId, programmerId);
                    bug.setId(id);
                    bugs.add(bug);
                }
            }
        }catch (SQLException e) {
            System.err.println("Error DB");
        }
        return bugs;
    }
}
