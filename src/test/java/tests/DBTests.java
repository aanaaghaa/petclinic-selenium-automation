package tests;

import com.anagha.petclinic.utils.DBUtils;
import java.sql.SQLException;
import java.util.List;

	/**
	 * A simple test class to verify database connectivity and fetch owner names.
	 * This class can be run independently to print all owners present in the database.
	 */

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
