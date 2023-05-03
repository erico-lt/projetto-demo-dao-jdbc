package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entites.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

    Connection conn;

    public DepartmentDaoJDBC (Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {        
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO department (Name, id) VALUE (?,?)";
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            ps.setString(1, obj.getName().toUpperCase());
            ps.setInt(2, obj.getId());
            int rows = ps.executeUpdate();

            conn.commit();
            System.out.printf("Success insert!!, Rows affected %d \n", rows);

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[ERRO] failure insert! Erro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {            
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Department obj) {
        String query = "UPDATE department SET name = ? WHERE (id = ?)";              
        PreparedStatement ps = null;
        try {            
            conn.setAutoCommit(false);            
            ps = conn.prepareStatement(query);
            ps.setString(1, obj.getName()); 
            
            int rows = ps.executeUpdate();  
            conn.commit();
            System.out.printf("Success update, rows affected %d \n", rows);  
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[ERRO] failure update, erro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DB.closeStatement(ps);
        }         
    }

    @Override
    public void deletById(Integer id) {
        String sql = "DELETE FROM department WHERE (Id = ?)";        
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsaffected = ps.executeUpdate();
            conn.commit();
            System.out.println("Success!!, rows affected: " + rowsaffected);
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[ERRO] failured Update, erro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }           
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Department findByiId(Integer id) {
        String query = "SELECT * FROM department WHERE (Id = ?);";        
        PreparedStatement ps = null;
        ResultSet rs = null;        
             
        try {
            Department department = new Department();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                Integer idReturn = rs.getInt("Id"); 
                department.setId(idReturn);
                department.setName(name);               
            }            
            conn.commit(); 
            return department;
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[ERRO] department not excist by:" + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DB.closeResult(rs);
            DB.closeStatement(ps);
        }
        return null;  
    }

    @Override
    public List<Department> findAll() {
        String query = "SELECT * FROM department";
        List<Department> listDepartment = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs= null;

        try {           
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                listDepartment.add(new Department(name, id));
            }

            conn.commit();
            return listDepartment;
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[EERO] problem acess list department, erro by: " + e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }            
        }
        return listDepartment;
        
    }
    
}
