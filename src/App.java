
import model.dao.*;
import model.entites.Seller;

public class App {
    public static void main(String[] args) {          
        
        SellerDao  sellerDao = DaoFactory.creatSellerDaoJDBC();        
        try {
           Seller seller =  sellerDao.findByiId(2);
           System.out.println(seller.toString());
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        
    }
}
