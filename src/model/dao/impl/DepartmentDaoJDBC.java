package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
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
            String sql = "INSERT INTO department (Name, id) VALUE (?,?)";
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
                e1.printStackTrace();
            }
        } finally {
            DB.closeConnection(con);
            DB.closeStatement(ps);
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
                e1.printStackTrace();
            }
        } finally {
            DB.closeConnection(con);
            DB.closeStatement(ps);
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
        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] failured Update, erro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }           
        } finally {
            DB.closeConnection(con);
            DB.closeStatement(ps);
        }
    }

    @Override
    public Department findByiId(Integer id) {
        String query = "SELECT * FROM department WHERE (Id = ?);";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String name;
        Integer idReturn;
        Department department = null;
        try {
            con = DB.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                name = rs.getString("Name");
                idReturn = rs.getInt("Id"); 
                department = new Department(name,idReturn);               
            }            
            con.commit(); 
            return department;
        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] department not excist by:" + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DB.closeConnection(con);
            DB.closeResult(rs);
            DB.closeStatement(ps);
        }
        return null;  
    }

    @Override
    public List<Department> findAll() {
        String query = "SELECT * FROM department";
        List<Department> listDepartment = new ArrayList<>();
        Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs= null;
        try {
            con = DB.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                listDepartment.add(new Department(name, id));
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[EERO] problem acess list department, erro by: " + e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }            
        }

        return listDepartment;
    }
    
}
