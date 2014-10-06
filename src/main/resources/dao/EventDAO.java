package com.accenture.adf.businesstier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;
import com.accenture.adf.helper.FERSDbQuery;

/**
 * <br/>
 * CLASS DESCRIPTION:<br/>
 * A Data Access Object (DAO) class for handling and managing event related data requested, updated, and 
 * processed in the application and maintained in the database.  The interface between the application and 
 * event data persisting in the database.<br/>
 * 
 */
public class EventDAO {

	// LOGGER for handling all transaction messages in EVENTDAO
	private static Logger log = Logger.getLogger(EventDAO.class);

	//JDBC API classes for data persistence
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private FERSDbQuery query;

	//Default constructor for injecting Spring dependencies for SQL queries
	public EventDAO() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		query = (FERSDbQuery) context.getBean("SqlBean");
	}

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	DAO for displaying all the FESTIVALEVENTS in EVENT TABLE in Database <br/>
	 *  
	 *  <br/>
	 *  PSEUDOCODE: <br/>
	 *  Create a new connection to the database<br/>
	 *  Prepare a statement object using the connection that gets all the events from the event table <br/>
	 *  Execute the SQL statement and keep a reference to the result set<br/>
	 *  Using a WHILE LOOP, store each record in the result set returned in an Event object<br/>
	 * 	Return the ArrayList to the calling method<br/>
	 * 
	 * @return collection of all Events (type ArrayList<Event>)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *	  
	 */
	public ArrayList<Event> showAllEvents() throws ClassNotFoundException,
			SQLException {
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		
        // TODO:  Add code here.....
		connection=FERSDataConnection.createConnection();
		statement=connection.prepareStatement(query.getSearchEvent());
		log.info("Query to be executed is-: "+query.getSearchEvent());
		resultSet=statement.executeQuery();
		Event event;
		while(resultSet.next())
		{
			event=new Event();
			event.setEventid(resultSet.getInt(1));
			event.setName(resultSet.getString(2));
			event.setDescription(resultSet.getString(3));
			event.setPlace(resultSet.getString(4));
			event.setDuration(resultSet.getString(5));
			event.setEventtype(resultSet.getString(6));
			event.setSeatsavailable(resultSet.getInt(7));
			eventList.add(event);
			
		}
		log.info("Number of records retrieved-:"+eventList.size());
		log.info("All Events retreived from Database :" + eventList);
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document   

		resultSet.close();
	    FERSDataConnection.closeConnection();
		return eventList;
	}

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	DAO for updating FESTIVAL EVENTS after visitor registers for event<br/>
	 *  
	 *  <br/>
	 *  PSEUDOCODE: <br/>
	 *  Create a new connection to the database <br/>
	 *  Prepare a statement object using the connection <br/> 
	 *  that uses the query that decreases the seats available for the event by 1 <br/>
	 *  If no records are updated or a SQL Error occurs throw a FERSGenericException with the <br/>
	 *  message stating the event record was not updated.
	 * 
	 * @param eventid (type int)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 *	  
	 */
	
	public void updateEventNominations(int eventid)
			throws ClassNotFoundException, SQLException, Exception {
		
		connection=FERSDataConnection.createConnection();
		statement=connection.prepareStatement(query.getUpdateEvent());
		log.info("Query to be executed is-: "+query.getUpdateEvent());
		
			statement.setInt(1, eventid);
		
		int number_of_row_updated=statement.executeUpdate();
		
		if(number_of_row_updated<=0)
		{
			log.info("Update was unsuccessfull");
			throw new FERSGenericException("Event record was not updated",new Exception());
		}
		
		log.info("Update successful. Number of rows updated is-:"+number_of_row_updated);
		
	    FERSDataConnection.closeConnection();
		// TODO:  Add code here.....
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document   

		

	}

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	DAO for checking visitor has already registered for EVENT or not. Sends boolean values about status.<br/>
	 *  
	 *  <br/>
	 *  PSEUDOCODE: <br/>
	 *  Create a new connection to the database <br/>
	 *  Prepare a statement object using the connection	<br/>
	 *  that uses a query to use gets a count of the number of times <br/> 
	 *  the visitor signed up for an event from EVENTSESSIONSIGNUP table <br/>
	 * 	Check the count:  If the count is 1 or more, return TRUE, else return FALSE
	 * 
	 * 	@param visitor (type Visitor)
	 * 	@param eventid (type int)
	 * 
	 * 	@throws ClassNotFoundException
	 * 	@throws SQLException 
	 *	  
	 */
	
	public boolean checkEventsofVisitor(Visitor visitor, int eventid)
			throws ClassNotFoundException, SQLException {
				
		int status = 0;
		log.info("Visitor ID-"+visitor.getVisitorId()+", Visitor name-"+visitor.getFirstName()+" "+visitor.getLastName()+", Event id-"+eventid);
		
		// TODO:  Add code here.....
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document   

		connection=FERSDataConnection.createConnection();
		statement=connection.prepareStatement(query.getCheckEvent());
		statement.setInt(1,eventid);
		statement.setInt(2,visitor.getVisitorId());
		log.info("Query to be executed is-: "+query.getCheckEvent());
		resultSet = statement.executeQuery();
		if(resultSet.next())
		{
			status = resultSet.getInt(1);
		}
		
		log.info("Number of times user registered is "+status);
		
		resultSet.close();
	    FERSDataConnection.closeConnection();
		
		if (status >= 1)
			return true;
		else
			return false;
		
	}

	/**
	 * 	<br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	DAO for update event database after unregistering event by visitor.<br/>
	 *
	 * 	@param eventid (type int)
	 * 
	 * 	@throws ClassNotFoundException
	 * 	@throws SQLException
	 *  
	 */

	public void updateEventDeletions(int eventid)
			throws ClassNotFoundException, SQLException, Exception {

		// creating new connection
		connection = FERSDataConnection.createConnection();
		
		// initializing statement object with connection object
		statement = connection.prepareStatement(query.getUpdateDeleteEvent());
		statement.setInt(1, eventid); //adding first parameter
		
		int status = statement.executeUpdate();
		
		if (status <= 0)
			throw new FERSGenericException("Records not updated properly",
					new Exception());
		log.info("Event registration status was updated in Database and Seat released");
		
		FERSDataConnection.closeConnection();

	}
}
