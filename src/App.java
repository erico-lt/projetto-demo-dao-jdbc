import model.dao.*;
import model.entites.Department;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        DepartmentDao departmentDao = DaoFactory.creatDepartmentDaoJDBC();
        Department publicidade = new  Department("limpeza", 6);
        try {
            departmentDao.update(publicidade);
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
    }
}
