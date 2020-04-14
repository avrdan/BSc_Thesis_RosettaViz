package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.*;
public class TimeReader {
	private ArrayList<Date> timeArray;
	Timestamp start, end;
	public TimeReader(Date startDate, Date endDate)
	{
		
		timeArray = new ArrayList<Date>();
		System.out.println("Start Date: " + startDate);
		System.out.println("End Date: " + endDate);
		PreparedStatement statement;
		
		start = new Timestamp(startDate.getTime());
		end = new Timestamp(endDate.getTime());
		try {
			statement = ConnectToDatabase.con.prepareStatement("SELECT pos_time FROM rosetta_pos WHERE pos_id % 3 = 1" +
					" AND (pos_time BETWEEN '" + start + "' AND '" + end + "')");
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				//System.out.println("adding date...");
				timeArray.add(new Date(result.getTimestamp("pos_time").getTime()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		System.out.println("Time array size is: " + timeArray.size());
	}
	
	public ArrayList<Date> getTimeArray()
	{
		return timeArray;
	}
	
}
