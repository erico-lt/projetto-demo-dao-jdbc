import model.dao.*;
import model.entites.Department;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        DepartmentDao departmentDao = DaoFactory.creatDepartmentDaoJDBC();        
        Department department = null;
        try {
            department = departmentDao.findByiId(5);
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        System.out.println(department.toString());
    }
}
