package model.dao;

import java.util.List;

import model.entites.Department;

public interface DepartmentDao {    
    abstract void insert (Department obj);
    abstract void update (Department obj);
    abstract void deletById (Integer id);
    abstract Department findByiId(Integer id);
    abstract List<Department> findAll();
}
