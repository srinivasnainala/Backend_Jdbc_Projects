package jdbcPractise;
import java.sql.*;
import java.util.*;

public class Travellers_Project1 {
public static void main(String[] args) throws Exception {
	Connection connection=null;
	Statement statement=null;
	PreparedStatement ps=null;
	String url="jdbc:mysql://localhost:3306/vasusql";
	String username="root";
	String password="M1racle@123";
	try
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection(url,username,password);
		statement=connection.createStatement();
		System.out.println("Enter username or MobileNumber");
		Scanner sc=new Scanner(System.in);
		String user=sc.nextLine();
		System.out.println();
//		String query="select count(*) as count from travellers where username='"+user+"' or Mobileno='"+user+"'";
		String query="select username from travellers where username='"+user+"' or Mobileno='"+user+"'";
		ResultSet rs=statement.executeQuery(query);
		if(rs.next()&&rs.getString("username").equals(user))
		{
			System.out.println("Enter your password : ");
			String pass=sc.nextLine();
			System.out.println();
			query="select count(*) as count from travellers where (username='"+user+"' or Mobileno='"+user+"') and password='"+pass+"'";
			rs=statement.executeQuery(query);
			if(rs.next()&&rs.getInt("count")==1)
			{
				query="select password from travellers where (username='"+user+"' or Mobileno='"+user+"') and password='"+pass+"'";
				rs=statement.executeQuery(query);
				if(rs.next()&&rs.getString("password").equals(pass))
				{
				System.out.println("Welcome to HOME_TOURS");
				System.out.println("Where you want to go?\n Enter Destination :");
				String Dest=sc.nextLine();
				System.out.println();
				System.out.println("From which location enter city name :");
				String Start=sc.nextLine();
				System.out.println();
				query="select count(*) as count from busses where Destination='"+Dest+"' and Starting_point='"+Start+"'";
				rs=statement.executeQuery(query);
				if(rs.next()&&rs.getInt("count")>0)
				{
					System.out.println("Available busses list is :");
					System.out.println();
					query="select * from busses where Destination='"+Dest+"' and Starting_point='"+Start+"'";
					rs=statement.executeQuery(query);
					ResultSetMetaData rsm=rs.getMetaData();
					int col=rsm.getColumnCount();
					while(rs.next())
					{
						for(int i=1;i<=col;i++)
						{
							System.out.print(rs.getString(i)+" ");
						}
						System.out.println();
					}
					System.out.println();
					System.out.println("Book your tickets here..!\n to continue press 1 \n to exit press 2 ");
					int n=sc.nextInt();
					sc.nextLine();
					switch(n)
					{
					case 1:
						System.out.println(" Have a Nice day..!");
                        query="select * from busses where Destination='"+Dest+"'";
    					rs=statement.executeQuery(query);
    					 rsm=rs.getMetaData();
    					 col=rsm.getColumnCount();
    					while(rs.next())
    					{
    						for(int i=1;i<=col;i++)
    						{
    							System.out.print(rs.getString(i)+" ");
    						}
    						System.out.println();
    					}
    					System.out.println();
    					System.out.println("On which travels you want to book your ticket? ");
    					System.out.println("As per the available busses Enter an id :");
    					int opt=sc.nextInt();
    					sc.nextLine();
    					switch(opt)
    					{
    					case 1:
    						System.out.println("Welcome to Orange travels..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 2:
    						System.out.println("Welcome to KVR travels..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 3:
    						System.out.println("Welcome to Morningstar Travels..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 4:
    						System.out.println("Welcome to volvo travels..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 8:
    						System.out.println("Welcome to Aps-Rtc..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 9:
    						System.out.println("Welcome to Kaveri travels..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 10:
    						System.out.println("Welcome to SaiKrishna Travels..");
    						Ticket_Bookings(connection,sc);
    						break;
    					case 11:
    						System.out.println("Welcome to Aps-Rtc Luxury..");
    						Ticket_Bookings(connection,sc);
    						break;
    						default:
    							System.out.println("Choose valid option");
    					}
						break;
						
					case 2:
						System.out.println("Exiting");
						System.exit(0);
						default:
							System.out.println("Choose valid choice");
					}
			}
				else
				{
					System.out.println("Oops..! No busses available for that destination\n please stay_tune\n Thanks for Visting..!");
				}
			}
				else
				{
					System.out.println("Password Mismatch");
				}
			}
			else
			{
				System.out.println("Password incorrect");
			}
		}
		else
		{
			System.out.println("User not registered");
			System.out.println("If you want to register choose following options \n 1 for registration \n 2 for exit");
			int n=sc.nextInt();
			sc.nextLine();
			switch(n)
			{
			case 1:
			System.out.println("Enter new username");
			username=sc.nextLine();
			query="select count(*) as count from travellers where username='"+username+"'";
			rs=statement.executeQuery(query);
			if(rs.next()&&rs.getInt("count")>0)
			{
			System.out.println("Username already exists..! please set another one");
			}
			else
			{
			System.out.println("Set your password");
			String pass=sc.nextLine();
			System.out.println("Re-enter your password");
			String pass1=sc.nextLine();
			if(pass.equals(pass1))
			{
				System.out.println("Enter your village");
				String vil=sc.nextLine();
				System.out.println("Enter your District");
				String dist=sc.nextLine();
				System.out.println("Enter your MobileNo");
				String Mon=sc.nextLine();
				query="insert into travellers(username,password,village,district,mobileno) values(?,?,?,?,?)";
				ps=connection.prepareStatement(query);
				ps.setString(1, username);
				ps.setString(2,pass);
				ps.setString(3, vil);
				ps.setString(4, dist);
				ps.setString(5, Mon);
				int res=ps.executeUpdate();
				if(res!=-1)
				{
					System.out.println("Registration Completed");
					System.out.println("Thanks for registering on HOME_TOURS..!");
				}
				else
				{
					System.out.println("Failed to insert");
				}
			}
			else
			{
				System.out.println("Password must be same");
			}
			}
			break;
			case 2:
				System.out.println("Exiting");
				System.exit(0);
			default:
			    System.out.println("Choose valid option..!");		
			}
		}
		rs.close();
		sc.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	finally 
	{
	statement.close();
	connection.close();
	
	}
}
public static void Ticket_Bookings(Connection connection,Scanner sc) throws Exception
{
	PreparedStatement ps=null;
	System.out.println("Enter your name :");
	String name=sc.nextLine();
	System.out.println("Enter your age :");
	int age=sc.nextInt();
	sc.nextLine();
	System.out.println("Enter your Mobile number :");
	String Mobileno=sc.nextLine();
	System.out.println("Enter your Gender :");
	String gender=sc.nextLine();
	String query="insert into Ticket_Bookings(name,age,MobileNo,Gender) values(?,?,?,?)";
	ps=connection.prepareStatement(query);
	ps.setString(1,name);
	ps.setInt(2, age);
	ps.setString(3,Mobileno);
	ps.setString(4, gender);
	int res=ps.executeUpdate();
	if(res!=-1)
	{
		System.out.println("Ticket Confirmed");
		System.out.println("Have a safe Journey...");
	}
	else
	{
		System.out.println("Something went wrong..!");
	}
}
}
