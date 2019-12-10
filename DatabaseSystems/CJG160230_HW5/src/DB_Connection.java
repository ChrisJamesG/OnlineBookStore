import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class DB_Connection {
	static Scanner input = new Scanner(System.in);
	static Connection con;
	static Statement stmt;
	static String userID;
	
	public static Connection dbCon()
	{
		// Attempt to connect to database on server computer
		try 
		{
			//load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Create connection url
			String url = "jdbc:oracle:thin:@localhost:1521/orclpdb";
			String user = "BOOKDBA";
			String pass = "minigrr1";
			
			//create the connection object
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection made to PDB");
			return con;
		}
		catch(Exception e)
		{
			System.out.print(e);
			return null;
		}
	}
	
	public static void bookInfo()
	{
		
	}
	
	public static void browseSubject()
	{
		ArrayList<String> subjects = new ArrayList<String>();
		int count = 1;
		
		try
		{
			//create the statement object
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select DISTINCT subject from books order by subject");
			while(rs.next())
			{
				System.out.println(count + ". " + rs.getString("subject"));
				subjects.add(rs.getString("subject"));
				count++;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		System.out.println("Enter your choice: ");
		String choice = input.nextLine();
		String search = subjects.get(Integer.parseInt(choice) - 1);
		count = 1;
		
		try
		{
			//create the statement object
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select author, title, ISBN, price, subject from books where subject = '" + search + "'");
			System.out.println("Query Executed");
			while(rs.next())
			{
				System.out.println("Author: " + rs.getString("author"));
				System.out.println("Title: " + rs.getString("title"));
				System.out.println("ISBN: " + rs.getString("ISBN"));
				System.out.println("Price: " + rs.getString("price"));
				System.out.println("Subject: " + rs.getString("subject"));
				System.out.println("");
				if(count % 2 == 0)
				{
					System.out.println("Enter ISBN to add to Cart or \n" + 
							"Enter to continue to browse or \n" + 
							"ENTER to go back to menu: " + 
							"");
					choice = input.nextLine();
					if(choice.isEmpty())
					{
						break;
					}
					else if(choice.equals("n"))
					{
						System.out.println("\nNext two books: \n");
					}
					else
					{
						String ISBN = choice;
						System.out.println("Enter Quantity: ");
						String quantity = input.nextLine();
						try
						{
							//create the statement object
							stmt = con.createStatement();
							stmt.executeUpdate("insert into cart values('" + userID + "'," + ISBN + "'," + quantity + "')");
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
					}
				}
				count++;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static void browseAuthorTitle()
	{
		
	}
	
	public static void cart()
	{
		
	}
	
	public static void orderStatus()
	{
		
	}
	
	public static void checkOut()
	{
		
	}
	
	public static void oneClick()
	{
		
	}
	
	public static void userInfo()
	{
		
	}
	
	public static void menu()
	{
		String option = "";
		
		while(!option.equals("8"))
		{
			// Print out welcome strings
			System.out.println("**********************************************************************");
			System.out.println("*** 								   ***");
			System.out.println("*** 	      Welcome to the Online Book Store Member Menu                ***");
			System.out.println("*** 								   ***");
			System.out.println("**********************************************************************");
			System.out.println("1. Browse by Subject \n2. Browse by Author/Title \n3. View Cart \n4. Order Status"
					+ "\n5. Check Out \n6. One Click Check Out \n7. View/Edit Personal Information \n8. Logout");
			
			option = input.nextLine();
			if(option.equals("1"))
				browseSubject();
			else if(option.equals("2"))
				browseAuthorTitle();
			else if(option.equals("3"))
				cart();
			else if(option.equals("4"))
				orderStatus();
			else if(option.equals("5"))
				checkOut();
			else if(option.equals("6"))
				oneClick();
			else if(option.contentEquals("7"))
				userInfo();
			else if(option.equals("8"))
				System.out.println("You have successfully logged out.");
			else
				System.out.println("Invalid option");
		}
	}
	
	public static void createMem()
	{
		String fname, lname, street, city, state, 
		zip, phone, email, userID, password, choice;
		String cardType = "";
		String creditCard = "";
		
		System.out.println("Welcome to the Online Book Store \n     New Member Registration");
		System.out.println("Enter first name: ");
		fname = input.nextLine();
		System.out.println("Enter last name: ");
		lname = input.nextLine();
		System.out.println("Enter street address: ");
		street = input.nextLine();
		System.out.println("Enter city: ");
		city = input.nextLine();
		System.out.println("Enter state: ");
		state = input.nextLine();
		System.out.println("Enter zip: ");
		zip = input.nextLine();
		System.out.println("Enter phone: ");
		phone = input.nextLine();
		System.out.println("Enter email: ");
		email = input.nextLine();
		System.out.println("Enter userID: ");
		userID = input.nextLine();
		System.out.println("Enter password: ");
		password = input.nextLine();
		
		System.out.println("Do you wish to store credit card information? (y/n): ");
		choice = input.nextLine();
		if(choice.equals("y"))
		{
			System.out.println("Enter type of Credit Card (amex/visa)");
			cardType = input.nextLine();
			while(creditCard.length() != 16)
			{
				System.out.println("Enter Credit Card Number: ");
				creditCard = input.nextLine();
				if(creditCard.length() != 16);
					System.out.println("Invalid Entry");
			}
		}
		else if(choice.equals("n"))
			System.out.println("No card information recorded");
		
		try
		{
			//create the statement object
			stmt = con.createStatement();
			stmt.executeUpdate("insert into members values('" + fname + "','" + lname + "','" + street + "','"
					+ city + "','" + state + "','" + zip + "','" + phone + "','" + email + "','" + userID + "','"
					+ password + "','" + cardType + "','" + creditCard + "')");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("You have registered successfully");
		
		System.out.println("First name: " + fname);
		System.out.println("Last name: " + lname);
		System.out.println("Address: " + street);
		System.out.println("City: " + city);
		System.out.println("State: " + state);
		System.out.println("ZipCode: " + zip);
		System.out.println("Phone number: " + phone);
		System.out.println("Email address: " + email);
		System.out.println("UserID: " + userID);
		System.out.println("Password: " + password);
		System.out.println("Credit Card Type: " + cardType);
		System.out.println("Credit Card Number: " + creditCard);
	}
	
	public static void login()
	{
		String pass;
		boolean validLogin = false;
		while(validLogin == false)
		{
			System.out.println("Enter userID: ");
			userID = input.nextLine();
			System.out.println("Enter password: ");
			pass = input.nextLine();
			validLogin = true;
		}
		menu();
	}

	public static void main(String[] args) {
		String choice = "";
		con = dbCon();
		
		while(!choice.equals("q"))
		{
			// Print out welcome strings
			System.out.println("**********************************************************************");
			System.out.println("*** 								   ***");
			System.out.println("*** 	        Welcome to the Online Book Store                   ***");
			System.out.println("*** 								   ***");
			System.out.println("**********************************************************************");
			System.out.println("1. Member Login \n2. New Member Registration \nq. Quit");
			
			choice = input.nextLine();
			if(choice.equals("1"))
				login();
			else if(choice.equals("2"))
				createMem();
		}
		
		input.close();
		try 
		{
			con.close();
		}
		catch (SQLException e) 
		{
			System.out.println(e);
		}
	}

}
