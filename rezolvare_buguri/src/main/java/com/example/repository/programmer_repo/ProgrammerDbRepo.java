package com.example.repository.programmer_repo;

import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.utils.JdbcUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class ProgrammerDbRepo implements ProgrammerRepo{

    private JdbcUtils dbUtils;

    public ProgrammerDbRepo(Properties props) {
        dbUtils=new JdbcUtils(props);
    }
    public Programmer findProgrammer(String username, String password) {
        Connection con = dbUtils.getConnection();
            Programmer programmer = null;
            try (PreparedStatement preStm = con.prepareStatement("SELECT * from programmers where username = ? and password = ?")) {
                preStm.setString(1, username);
                preStm.setString(2, password);
                try (ResultSet result = preStm.executeQuery()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String companyName = result.getString("company_name");
                    programmer = new Programmer(name, username, password, companyName);
                    programmer.setId(id);
                }
            } catch (SQLException ignored) {}
        return programmer;
    }

    @Override
    public void add(Programmer elem) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStm=con.prepareStatement("insert into programmers (name, username, password, company_name) values (?,?,?,?)")){
            preStm.setString(1, elem.getName());
            preStm.setString(2, elem.getUsername());
            preStm.setString(3, elem.getPassword());
            preStm.setString(4, elem.getCompanyName());
            preStm.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB "+ex);
        }
    }

    /*public void add(Programmer elem){
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(elem);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        close();
    }*/

    @Override
    public void update(Integer integer, Programmer elem) {

    }

    @Override
    public Iterable<Programmer> findAll() {
        return null;
    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
}
