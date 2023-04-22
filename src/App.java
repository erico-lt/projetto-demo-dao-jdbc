
import java.util.List;

import model.dao.*;
import model.entites.Department;
import model.entites.Seller;

public class App {
    public static void main(String[] args) {          
        
        SellerDao  sellerDao = DaoFactory.creatSellerDaoJDBC();        
        try {
            Department dep = new Department(null, 5);
            List<Seller> listSeller = sellerDao.findByDepartment(dep);
            listSeller.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        
    }
}
