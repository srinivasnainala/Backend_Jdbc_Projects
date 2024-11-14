package jdbcPractise;
import java.sql.*;
import java.util.*;

class my extends Exception
{
	String s;
	my(String s)
	{
		super(s);
	}
}
public class customExceptionForAtmConnectingtoDatabase {
public static void main(String[] args) throws Exception {
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection connection=null;
	Statement statement=null;
	String url="jdbc:mysql://localhost:3306/vasusql";
	String username="root";
	String password="M1racle@123";
try  
{
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter your cardNumber :");
	String cardno=sc.nextLine();
	connection=DriverManager.getConnection(url,username,password);
	statement=connection.createStatement();
	String query="select count(*) as count from atm where cardnumber='"+cardno+"'";
	ResultSet rs=statement.executeQuery(query);
	if(rs.next()&&rs.getInt("count")>0)
	{
		System.out.println("Card founded...");
		System.out.println("Choose 1 for cash-Withdraw");
		System.out.println("Choose 2 for Balance Enquiry");
		System.out.println("Choose 3 for Deposit");
		System.out.println("Choose 4 for exit");
		int n=sc.nextInt();
		sc.nextLine();
		switch(n)
		{
		case 1:
			System.out.println("Enter money to withdraw :");
			int withdraw=sc.nextInt();
			sc.nextLine();
		    query="select balance from atm where cardnumber='"+cardno+"'and balance>="+withdraw;
		    rs=statement.executeQuery(query);
		    if(rs.next()&&rs.getInt("balance")>=withdraw)
		    {
		    	System.out.println("Enter your pin");
		    	int pin=sc.nextInt();
		    	sc.nextLine();
		        System.out.println("processing...");
		    	query="select count(*) as count from atm where cardnumber='"+cardno+"'and pin="+pin;
		        rs=statement.executeQuery(query);
		        if(rs.next()&&rs.getInt("count")>0)
		        {
		        	System.out.println("Collect your cash..");
		        	rs=statement.executeQuery("select balance from atm where cardnumber='"+cardno+"'and pin="+pin);
		            if(rs.next())
		            {
		        	int balance=rs.getInt("balance");
		        	System.out.println("Debited amount is "+withdraw);
		        	int updatedbal=balance-withdraw;
		        	int result=statement.executeUpdate("update atm set balance="+updatedbal+" where cardnumber='"+cardno+"'");
		        	if(result!=-1)
		        	{
		        		System.out.println("Remaining amount is :"+updatedbal);
		        	}
		            }
		        }  
		        else
		        {
		        	throw new my("Password incorrect");
		        }
		    }
		    else
		    {
		    	throw new my("Insufficient funds");
		    }
		    break;
		case 2:
			System.out.println("Enter your pin :");
			int pin=sc.nextInt();
			sc.nextLine();
			rs=statement.executeQuery("select count(*) as count from atm where cardnumber='"+cardno+"' and pin="+pin);
			if(rs.next()&&rs.getInt("count")>0)
			{
				rs=statement.executeQuery("select balance from atm where cardnumber='"+cardno+"' and pin="+pin);
				if(rs.next())
				{
				int balance=rs.getInt("balance");
				System.out.println("your balance is :"+balance);
				}
			}
			else
			{
				throw new my("Password incorrect");
			}
			break;
		case 3:
			System.out.println("Enter money deposit :");
			int deposit=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter your pin :");
			 pin=sc.nextInt();
			 sc.nextLine();
			 rs=statement.executeQuery("select count(*) as count from atm where cardnumber='"+cardno+"'and pin="+pin);
			 if(rs.next()&&rs.getInt("count")>0) 
			 {
			 rs=statement.executeQuery("select balance from atm where cardnumber='"+cardno+"'");
			 if(rs.next())
			 {
				int balance=rs.getInt("balance");
				int updatedbal=balance+deposit;
				int res = statement.executeUpdate("UPDATE atm set balance = " + updatedbal + " WHERE cardnumber = '" + cardno + "'");
				if(res!=-1)
				{
					System.out.println("Deposited successfully");
				}
				else
				{
					throw new my("Failed to deposit");
				}
			 }
			 }
			 else
			 {
				 throw new my("Invalid pin");
			 }
			break;
		case 4:
			System.out.println("Remove your card");
			System.exit(0);
			default:
				System.out.println("Choose valid option");
		}
	}
	else
	{
		throw new my("card not found");
//		System.out.println("card not found..!");
//		System.out.println("Enter 1 for register or 2 for exit");
//		int n=sc.nextInt();
//		switch(n)
//		{
//		case 1:
//		System.out.println("Enter your cardnumber :");
//		cardno=sc.nextLine();
////		sc.nextLine();
//	    int pin=sc.nextInt();
//		query="insert into atm(cardnumber, pin) values('"+cardno+"', "+pin+ ")";
////	    query = "INSERT INTO atm(cardnumber, pin) VALUES('" + cardno + "', " + pin + ")";
//		int res1=statement.executeUpdate(query);
//		if(res1!=-1)
//		{
//			System.out.println("Pin generated Successfully");
//		}
//		else
//		{
//			System.out.println("Failed to generate ");
//		}
//		break;
//		case 2:
//			System.out.println("Exit");
//			System.exit(0);
//		default:
//			System.out.println("choose valid option");
//		}
	}
}
catch (Exception e) 
{
	System.out.println(e);
}
}
}
