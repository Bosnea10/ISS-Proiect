package com.example.repository.bug_repo;

import com.example.domain.Bug;
import com.example.domain.Programmer;
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

    private Programmer crtProgrammer;

    public Programmer getCrtProgrammer() {
        return crtProgrammer;
    }

    public void setCrtProgrammer(Programmer crtProgrammer) {
        this.crtProgrammer = crtProgrammer;
    }

    public BugDbRepo(Properties props) {
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Bug elem) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into bugs (name, description, project_name) values (?,?,?)")){
            preStm.setString(1, elem.getName());
            preStm.setString(2, elem.getDescription());
            preStm.setString(3, elem.getProjectName());
            preStm.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Bug bug) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("UPDATE bugs SET name = ?, description = ?, project_name = ? WHERE id = ?")){
            preStm.setString(1, bug.getName());
            preStm.setString(2, bug.getDescription());
            preStm.setString(3, bug.getProjectName());
            preStm.setInt(4, bug.getId());
            preStm.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error DB");
        }
    }

    @Override
    public void updateProgrammer(Bug bug) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("UPDATE bugs SET programmer_name = ? WHERE id = ?")){
            preStm.setString(1, bug.getProgrammerName());
            preStm.setInt(2, bug.getId());
            preStm.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error DB");
        }
    }

    @Override
    public void updateUnresolved(Bug bug) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("UPDATE bugs SET programmer_name = NULL WHERE id = ?")){
            preStm.setInt(1, bug.getId());
            preStm.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error DB");
        }
    }


    @Override
    public List<Bug> findAll() {
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

    @Override
    public List<Bug> findAllAssigned(Programmer programmer){
        Connection con= dbUtils.getConnection();
        List<Bug> bugs=new ArrayList<>();
        try(PreparedStatement preStm=con.prepareStatement("select * from bugs where programmer_name = ?")){
            preStm.setString(1, programmer.getName());
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

    @Override
    public void delete(Bug bug) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("DELETE FROM bugs WHERE id = ?")){
            preStm.setInt(1, bug.getId());
            preStm.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error DB");
        }
    }
}
