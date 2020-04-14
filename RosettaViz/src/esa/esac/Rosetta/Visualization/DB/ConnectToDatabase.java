package esa.esac.Rosetta.Visualization.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class ConnectToDatabase {
	public static Connection con;
	
		public ConnectToDatabase(String db_name, String userName, String password)
		{
			// Accessing driver from the JAR file
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(null,
					    "Something went wrong.\nNo database driver?",
					    "Database connection error",
					    JOptionPane.ERROR_MESSAGE);
				
				e1.printStackTrace();
			}
			// Creating a variable for the connection called "con"
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vex_ops", "vsoc_plan", "va7ethU8");
			
			try
			{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, userName, password);
				if(con.isValid(0))
					System.out.println("Connection established! Database valid!");
				
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,
					    "Could not connect to database! Please check that the DB is specified correctly.",
					    "Database connection error",
					    JOptionPane.ERROR_MESSAGE);
			}
			

		}
}
