package jdbcPractise;
import java.util.*;
import java.sql.*;
public class Office_Database_Administration_Project {
public static void main(String[] args) throws Exception {
	Connection connection=null;
	Statement statement=null;
	PreparedStatement ps=null;
	String url="jdbc:mysql://localhost:3306/DBA";
	String username="root";
	String password="M1racle@123";
	try
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection(url,username,password);
		statement=connection.createStatement();
		Scanner sc=new Scanner(System.in);
		System.out.println("-----------------------------LOGIN_PAGE-----------------------------------");
		System.out.println();
		System.out.println("            1 for Admin_Login               2 for User_Login");
		int opt=sc.nextInt();
		sc.nextLine();
		switch(opt)
		{
		case 1:
		System.out.println("Enter username :");
		String user=sc.nextLine();
		String query="select count(*) as count from Admin where username='"+user+"'";
		ResultSet rs=statement.executeQuery(query);
		if(rs.next()&&rs.getInt("count")==1)
		{
			 query="select username from Admin where username='"+user+"'";
		     rs=statement.executeQuery(query);
			if(rs.next()&&rs.getString("username").equals(user))
			{
			System.out.println("Enter your password :");
			String pass=sc.nextLine();
			query="select count(*) as count from Admin where username='"+user+"' and password='"+pass+"'";
			rs=statement.executeQuery(query);
            if(rs.next()&&rs.getInt("count")==1)
            {
			query="select password from Admin where username='"+user+"' and password='"+pass+"'";
			rs=statement.executeQuery(query);
			if(rs.next()&&rs.getString("password").equals(pass))
			{
				System.out.println("--------------------------welcome to "+user+"-------------------------------");
				System.out.println("What do you want to do?");
				System.out.println("Choose options \n 1 for add user\n 2 for Delete an user \n 3 for Update their data\n 4 for view EmployeeData");
				int n=sc.nextInt();
				sc.nextLine();
				switch(n)
				{
				case 1:
					AddUser(statement,rs,sc,connection,ps);
					break;
				case 2:
					System.out.println("Enter employee id to delete that Record");
					n=sc.nextInt();
					query="delete from employee where id="+n;
					ps=connection.prepareStatement(query);
					int result=ps.executeUpdate();
					if(result!=-1)
					{
						System.out.println("Employee deleted ");
					}
					else
					{
						System.out.println("Failed to delete");
					}
					break;
				case 3:
					Updating(connection,sc,statement);
					break;
					
				case 4:
					System.out.println("If you want to view Entire data?\n choose 1   or\n to view particular record? \n choose 2 ");
					n=sc.nextInt();
					switch(n)
					{
					case 1:
						RetriveEntireData(statement,rs,sc);
						break;
					case 2:
						RetriveById(statement,sc,rs);
					    break;
					default:
						System.out.println("Choose valid option");
					}
					break;
				default:
					System.out.println("Choose valid option..!");
				}
			}
            }
            else
            {
            	System.out.println("Password incorrect");
            }
		}
		else
        {
        	System.out.println("Enter valid Usernane");
        }
		}	
		else
		{
			System.out.println("Enter valid username");
		}
		break;
		case 2:
			System.out.println("Enter your username :");
			 user=sc.nextLine();
			 query="select count(*) as count from users1 where username='"+user+"'";
			 rs=statement.executeQuery(query);
			if(rs.next()&&rs.getInt("count")==1)
			{
				 query="select username from users1 where username='"+user+"'";
			     rs=statement.executeQuery(query);
				if(rs.next()&&rs.getString("username").equals(user))
				{
				System.out.println("Enter your password :");
				String pass=sc.nextLine();
				query="select count(*) as count from users1 where username='"+user+"' and password='"+pass+"'";
				rs=statement.executeQuery(query);
	            if(rs.next()&&rs.getInt("count")==1)
	            {
				query="select password from users1 where username='"+user+"' and password='"+pass+"'";
				rs=statement.executeQuery(query);
				if(rs.next()&&rs.getString("password").equals(pass))
				{
				   System.out.println("--------------------------welcome to "+user+"-------------------------------");
				   System.out.println("Enter your Designation : ");
				   String desg=sc.nextLine();
				   query="select designation from employee where designation='"+desg+"'";
				   rs=statement.executeQuery(query);
				   if(rs.next()&&rs.getString("designation").equals(desg)&&rs.getString("designation").equals("manager"))
				   {
					   System.out.println(" To update choose 1   \n To view Particular Record choose 2\n To view Entire Table data choose 3 \n To add an user choose 4");
					   opt=sc.nextInt();
					   sc.nextLine();
					   switch(opt)
					   {
					   case 1:
						   Updating(connection,sc,statement);
						   break;
					   case 2:
						   RetriveById(statement,sc,rs);
							break;
					   case 3:
						   RetriveEntireData(statement,rs,sc);
							break;
					   case 4:
						   AddUser(statement,rs,sc,connection,ps);
						   break;
						default:
							System.out.println("Choose valid option");
					   }
				   }
				   else if(rs.next()&&rs.getString("designation").equals(desg)&&rs.getString("designation").equals("teamlead"))
				   {
						System.out.println(" If you want to view Entire data?  choose 1   or\n to view particular record?  choose 2 or\n to update choose 3");
					   int n=sc.nextInt();
						switch(n)
						{
						case 1:
							  RetriveEntireData(statement,rs,sc);
							break;
						case 2:
							  RetriveById(statement,sc,rs);
							break;
					    case 3:
					    	 Updating(connection,sc,statement);
						    break;
						default:
								System.out.println("Choose valid option");
				        }
				   }
				   else if(rs.next()&&rs.getString("designation").equals(desg))
				   {
					   query="select count(*) as count from employee where designation='"+desg+"'";
					   rs=statement.executeQuery(query);
					   if(rs.next()&&rs.getInt("count")>=3)
					   {
					   System.out.println("If you want to view Entire data?\n choose 1   or\n to view particular record? \n choose 2 ");
					   int n=sc.nextInt();
						switch(n)
						{
						case 1:
							  RetriveEntireData(statement,rs,sc);
							break;
						case 2:
							  RetriveById(statement,sc,rs);
							break;
							default:
								System.out.println("Choose valid option");
				        }
					   }
				   }
				   else
				   {
					   System.out.println("Enter valid Designation");
				   }
	            }
				else
	            {
	            	System.out.println("password incorrect");
	            }
	            }
	        	else
				{
					System.out.println("password incorrect");
				}
				}
				else
				{
					System.out.println("Enter valid username");
				}
		   }
		    else
	       	{
			    System.out.println("Enter valid username");
	       	}
			break;
			default:
				System.out.println("Choose valid option");
	}
		sc.close();
	}
	catch (Exception e)
	{
		System.out.println(e);
	}
	finally
	{
		statement.close();
		connection.close();
	}
}
public static void Updating(Connection connection,Scanner sc,Statement statement) throws Exception
{
	System.out.println("Enter employee id to update his/her Data :");
   int n=sc.nextInt();
	System.out.println("Choose these \n 1 for update salary\n 2 for Designation\n 3 for name");
    int opt=sc.nextInt();
    sc.nextLine();
    switch(opt)
    {
    case 2:
	System.out.println("Enter new Designation :");
	String Desg=sc.nextLine();
	String query="update employee set designation='"+Desg+"' where id="+n;
	int result=statement.executeUpdate(query);
	if(result!=-1)
	{
		System.out.println("Employee designation updated successfully ");
	}
	else
	{
		System.out.println("Failed to update");
	}
	break;
    case 1:
    	System.out.println("Enter Salary :");
    	int salary=sc.nextInt();
    	query="update employee set salary="+salary+" where id="+n;
		result=statement.executeUpdate(query);
		if(result!=-1)
		{
			System.out.println("Employee salary updated successfully ");
		}
		else
		{
			System.out.println("Failed to update");
		}
    	break;
    case 3:
    	System.out.println("Enter new resignation :");
		String name=sc.nextLine();
		query="update employee set name='"+name+"' where id="+n;
		result=statement.executeUpdate(query);
		if(result!=-1)
		{
			System.out.println("Employee Name updated successfully ");
		}
		else
		{
			System.out.println("Failed to update");
		}
		break;
    }
}
public static void RetriveById(Statement statement,Scanner sc,ResultSet rs) throws Exception
{
	  System.out.println("Enter id to view his/her data");
		int n=sc.nextInt();
		String query="select * from employee where id="+n;
		rs=statement.executeQuery(query);
	    ResultSetMetaData rsm=rs.getMetaData();
		int count=rsm.getColumnCount();
		while(rs.next())
		{
			for(int i=1;i<=count;i++)
			{
				System.out.print(rs.getString(i)+" ");
			}
			System.out.println();
		}
}
public static void RetriveEntireData(Statement statement,ResultSet rs,Scanner sc) throws Exception
{
	String query="select * from employee";
	rs=statement.executeQuery(query);
	ResultSetMetaData rsm=rs.getMetaData();
	int count=rsm.getColumnCount();
	while(rs.next())
	{
		for(int i=1;i<=count;i++)
		{
			System.out.print(rs.getString(i)+" ");
		}
		System.out.println();
	}
}
public static void AddUser(Statement statement,ResultSet rs,Scanner sc,Connection connection,PreparedStatement ps) throws Exception
{
	System.out.println("Enter Name :");
	String name=sc.nextLine();
	System.out.println("Enter username for that user :");
	String user=sc.nextLine();
	String query="select username from users1 where username='"+user+"'";
	rs=statement.executeQuery(query);
	if(rs.next()&&rs.getString("username").equals(user))
	{
		System.out.println("Username already used..Please change it");
		System.out.println("Enter username :");
		user=sc.nextLine();
		 query="select username from users1 where username='"+user+"'";
			rs=statement.executeQuery(query);
			if(rs.next()&&rs.getString("username").equals(user))
			{
				System.out.println("Username already used..Please change it");
				System.out.println("Sorry Retry again...");
				System.exit(0);
			}
			else
			{
			System.out.println("Enter password :");
			String pass=sc.nextLine();
			System.out.println("Re-Enter your password");
			String pass1=sc.nextLine();
			if(pass.equals(pass1))
			{
				 query="insert into users1(name,username,password) values('"+name+"','"+user+"','"+pass+"')";
				 int result=statement.executeUpdate(query);
//		    query="insert into users1(name,username,password) values(?,?,?)";
//			ps=connection.prepareStatement(query);
//			ps.setString(1, name);
//			ps.setString(2,user);
//			ps.setString(3, pass1);
//			int result=ps.executeUpdate();
			if(result!=-1)
			{
				System.out.println("User added successfully");
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
	}
	else
	{
	System.out.println("Enter password :");
	String pass=sc.nextLine();
	System.out.println("Re-Enter your password");
	String pass1=sc.nextLine();
	if(pass.equals(pass1))
	{
    query="insert into users1(name,username,password) values(?,?,?)";
	ps=connection.prepareStatement(query);
	ps.setString(1, name);
	ps.setString(2,user);
	ps.setString(3, pass1);
	int result=ps.executeUpdate();
	if(result!=-1)
	{
		System.out.println("User added successfully");
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
}
}
