package model.dao;

import java.util.List;

import model.entites.Seller;

public interface SellerDao {
    abstract void insert (Seller obj);
    abstract void update (Seller obj);
    abstract void deletById (Integer id);
    abstract Seller findByiId(Integer id);
    abstract List<Seller> findAll();
}
