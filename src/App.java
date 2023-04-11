import model.dao.DaoFactory;
import model.dao.SellerDao;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        SellerDao sellerDao = DaoFactory.creatSellerDao();
    }
}
