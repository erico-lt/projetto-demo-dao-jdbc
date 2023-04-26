
import java.util.List;

import model.dao.*;
import model.entites.Seller;

public class App {
    public static void main(String[] args) {          
        
        SellerDao  sellerDao = DaoFactory.creatSellerDaoJDBC();        
        try {            
            List<Seller> listSeller = sellerDao.findAll();
            listSeller.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        
    }
}
