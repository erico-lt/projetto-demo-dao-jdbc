package model.dao;

import java.util.List;

import model.entites.Department;
import model.entites.Seller;

public interface SellerDao {
    abstract void insert (Seller obj);
    abstract void update (Seller obj);
    abstract void deletById (Integer id);
    abstract Seller findById(Integer id);
    abstract List<Seller> findAll();
    abstract List<Seller> findByDepartment(Department department);
}
