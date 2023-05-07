package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.*;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class SellerDaoJDBC implements SellerDao {
    Connection conn = null;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
                + " VALUE(?,?,?,?,?)";        
        PreparedStatement ps = null;

        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, 3);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                int id = rs.getInt(1);
                obj.setId(id);
            } else {
                throw new DbException("[ERRO] Seller not added");
            }
            conn.commit();
            System.out.printf("Success!! item added, rows affected %d \n", rowsAffected);
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[ERRO] failure addition, errro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller obj) {
        String query = "UPDATE seller SET Name = ?,Email = ?,BirthDate = ?,BaseSalary = ?,DepartmentId = ? WHERE (Id = ?)";        
        PreparedStatement ps = null;

        try {            
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, 3);
            ps.setInt(6, obj.getId());
            int rowsAffected = ps.executeUpdate();

            conn.commit();
            System.out.printf("Success!! Update, rows affected: %d \n", rowsAffected);
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("[ERRO] failure update!! Erro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deletById(Integer id) {
        String query = "DELETE FROM seller WHERE Id = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Sucess!! Rows affected:" + rowsAffected);
        } catch (SQLException e) {
            throw new DbException("[ERRO] failure delet, erro by: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT seller.*, department.Name as DepName"
                + " FROM seller INNER JOIN department"
                + " ON seller.DepartmentId = department.Id"
                + " WHERE seller.Id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Department dep = instantiatDep(rs);                
                Seller seller = instatiatSeller(rs, dep);                
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("[ERRO] Problem finding seller, erro by:" + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResult(rs);
        }

    }

    private Seller instatiatSeller(ResultSet rs, Department dep) throws SQLException{
        Integer idSele = rs.getInt("Id");
        String name = rs.getString("Name");
        String email = rs.getString("Email");
        Date date = rs.getDate("BirthDate");
        Double salary = rs.getDouble("BaseSalary");
        Seller seller = new Seller(idSele, name, email, date, salary);
        seller.setDepartment(dep);
        return seller;
    }

    private Department instantiatDep(ResultSet rs) throws SQLException{        
        return new Department(rs.getString("DepName"), rs.getInt("DepartmentId"));
    }

    @Override
    public List<Seller> findAll() {
        String query = "SELECT seller.*, department.Name as DepName"
        + " FROM seller INNER JOIN department" 
        + " ON seller.DepartmentId = department.Id";
        List<Seller> listSeller = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery(query);
            Map<Integer, Department> listDep = new HashMap<>();
            while (rs.next()) {                 
                Department dep = listDep.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiatDep(rs);
                    listDep.put(dep.getId(), dep);
                }
                Seller seller = instatiatSeller(rs, dep);   
                listSeller.add(seller);
            }

            return listSeller;
        } catch (SQLException e) {
            throw new DbException("[ERRO] failure, erro by: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResult(rs);
        }

    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        String query = "SELECT seller.*, department.Name as depName" 
                    + " FROM seller INNER JOIN department" 
                    + " ON seller.DepartmentID = department.Id" 
                    + " WHERE DepartmentId = ?" 
                    + " ORDER BY Name";
                    
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Seller> listSeller = new ArrayList<>();

        try {
            Map<Integer, Department> map = new HashMap<>();
            ps = conn.prepareStatement(query);
            ps.setInt(1, department.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiatDep(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instatiatSeller(rs, dep);
                listSeller.add(obj);
            }
            
            return listSeller;            
        } catch (SQLException e) {
            throw new DbException("[ERRO] failure acess, erro by: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResult(rs);
        }        
    }

}
