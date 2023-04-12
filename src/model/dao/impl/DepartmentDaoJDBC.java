package model.dao.impl;

import java.sql.*;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entites.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

    @Override
    public void insert(Department obj) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO department (name, id) VALUE (?,?)";
            con = DB.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            ps.setString(1, obj.getName().toUpperCase());
            ps.setInt(2, obj.getId());
            int rows = ps.executeUpdate();

            con.commit();
            System.out.printf("Success insert!!, Rows affected %d \n", rows);

        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] failure insert! Erro by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("[ERRO] erro in rolllback!!, erro by: " + e1.getMessage());
            }
        } finally {
            DB.closeConnection(con);
            DB.closeStatment(ps);
        }
    }

    @Override
    public void update(Department obj) {
        String query = "UPDATE department SET name = ? WHERE (id = ?)";
        Connection con = null;        
        PreparedStatement ps = null;
        try {
            con = DB.getConnection();
            con.setAutoCommit(false);            
            ps = con.prepareStatement(query);
            ps.setString(1, obj.getName()); 
            ps.setInt(2, obj.getId());
            int rows = ps.executeUpdate();  
            con.commit();
            System.out.printf("Success update, rows affected %d \n", rows);  
        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] failure update, erro by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("[ERRO] erro in rollback, erro by: " + e1.getMessage());
            }
        } finally {
            DB.closeConnection(con);
            DB.closeStatment(ps);
        }         
    }

    @Override
    public void deletById(Integer id) {
        String sql = "DELETE FROM department WHERE (Id = ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DB.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsaffected = ps.executeUpdate();
            con.commit();
            System.out.println("Success!!, rows affected: " + rowsaffected);
        } catch (Exception e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] failured Update, erro by: " + e.getMessage());
            } catch (Exception e1) {
                throw new DbException("[EERO] problem in rollback, erro by: " + e1.getMessage());
            }           
        } finally {
            DB.closeConnection(con);
            DB.closeStatment(ps);
        }
    }

    @Override
    public Department findByiId(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByiId'");
    }

    @Override
    public List<Department> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
