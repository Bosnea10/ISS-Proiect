package com.example.repository.tester_repo;

import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TesterDbRepo implements TesterRepo {

    private JdbcUtils dbUtils;

    public TesterDbRepo(Properties props) {
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Tester elem) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into testers (name, username, password, company_name) values (?,?,?,?)")){
            preStm.setString(1, elem.getName());
            preStm.setString(2, elem.getUsername());
            preStm.setString(3, elem.getPassword());
            preStm.setString(4, elem.getCompanyName());
            preStm.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DBa "+ex);
        }
    }

    @Override
    public void update(Tester elem) {

    }

    @Override
    public Iterable<Tester> findAll() {
        return null;
    }

    @Override
    public Tester findTester(String username, String password) {
        Connection con = dbUtils.getConnection();
        Tester tester = null;
        try (PreparedStatement preStm = con.prepareStatement("SELECT * from testers where username = ? and password = ?")) {
            preStm.setString(1, username);
            preStm.setString(2, password);
            try (ResultSet result = preStm.executeQuery()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String companyName = result.getString("company_name");
                tester = new Tester(name, username, password, companyName);
                tester.setId(id);
            }
        } catch (SQLException ignored) {}
        return tester;
    }
}
