import java.util.Date;
import java.util.List;

import model.dao.*;
import model.entites.Seller;

public class App {
    public static void main(String[] args) {       
        
        Seller seller = new Seller(7, "Erico Santana", "erico.blp@gmail.com", new Date(), 3000);
        SellerDao  sellerDao = DaoFactory.creatSellerDaoJDBC();
        try {
            sellerDao.update(seller);
        } catch (Exception e) {
            System.out.println("erro insert by: " + e.getMessage());
        }
        
    }
}
