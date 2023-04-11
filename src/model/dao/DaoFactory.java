package model.dao;

import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    
    public static SellerDao creatSellerDao () {
        return new SellerDaoJDBC();
    }

    public static DepartmentDao creatDepartmentDaoJDBC () {
        return new DepartmentDaoJDBC();
    }
}
