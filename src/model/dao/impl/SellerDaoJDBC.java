package model.dao.impl;

import java.sql.*;
import java.util.List;

import db.*;
import model.dao.SellerDao;
import model.entites.Seller;

public class SellerDaoJDBC implements SellerDao{

    @Override
    public void insert(Seller obj) {
        String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
        +" VALUE(?,?,?,?,?)";
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
            ps.setInt(5, 1);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deletById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletById'");
    }

    @Override
    public Seller findByiId(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByiId'");
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
