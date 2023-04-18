
import model.dao.*;

public class App {
    public static void main(String[] args) {          
        
        SellerDao  sellerDao = DaoFactory.creatSellerDaoJDBC();        
        try {
           sellerDao.deletById(7);
           System.out.println("Sucess!!");
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        
    }
}
