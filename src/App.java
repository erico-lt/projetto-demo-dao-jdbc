import java.util.List;

import model.dao.*;
import model.entites.Department;

public class App {
    public static void main(String[] args) {   
        
        DepartmentDao departmentDao = DaoFactory.creatDepartmentDaoJDBC();
        try {
            List<Department> departmentList = departmentDao.findAll();
            departmentList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        
    }
}
