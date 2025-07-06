package tests;

import com.anagha.petclinic.utils.DBUtils;
import java.sql.SQLException;
import java.util.List;

public class DBTests{
    public static void main(String[] args) {
        try {
            List<String> owners = DBUtils.getAllOwnerNames();
            System.out.println("Owners in DB:");
            for (String name : owners) {
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection();
        }
    }
}
