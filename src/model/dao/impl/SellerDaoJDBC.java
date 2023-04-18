package model.dao.impl;

import java.sql.*;
import java.util.List;

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
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DB.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, 3);
            int rowsAffected = ps.executeUpdate();
            con.commit();
            System.out.printf("Success!! item added, rows affected %d \n", rowsAffected);
        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] failure addition, errro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void update(Seller obj) {
        String query = "UPDATE seller SET Name = ?,Email = ?,BirthDate = ?,BaseSalary = ?,DepartmentId = ? WHERE (Id = ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DB.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, 3);
            ps.setInt(6, obj.getId());
            int rowsAffected = ps.executeUpdate();

            con.commit();
            System.out.printf("Success!! Update, rows affected: %d \n", rowsAffected);
        } catch (SQLException e) {
            try {
                con.rollback();
                throw new DbException("[ERRO] failure update!! Erro by: " + e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void deletById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletById'");
    }

    @Override
    public Seller findByiId(Integer id) {
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
                Department dep = new Department(rs.getString("DepName"), rs.getInt("DepartmentId"));
                Integer idSele = rs.getInt("Id");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                Date date = rs.getDate("BirthDate");
                Double salary = rs.getDouble("BaseSalary");
                Seller seller = new Seller(idSele, name, email, date, salary);
                seller.setDepartment(dep);
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

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
