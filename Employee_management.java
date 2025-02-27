package JDBC;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class EmployeeTask_3 {
	public static void addEmployee(Connection c,PreparedStatement p,Scanner sc,ResultSet rs) throws Exception
	{
		p=c.prepareStatement("insert into registerEmployee(firstName,lastName,phno,alternativephnum,cemail,designation,salary) values(?,?,?,?,?,?,?)");
	    sc.nextLine();
		System.out.println("Enter firstname:");
		String fname=sc.nextLine();
		System.out.println("Enter lastname:");
		String lname=sc.nextLine();
		System.out.println("Enter phnum:");
		String phnum=sc.nextLine();
		System.out.println("Enter alternative phnum:");
		String aphnum=sc.nextLine();
		String cemail=fname.substring(0,1)+lname+"@miraclesoft.com";
		System.out.println("Enter designation:");
		String desig=sc.nextLine();
		System.out.println("Enter salary:");
		int salary=sc.nextInt();
		p.setString(1, fname);
		p.setString(2, lname);
		p.setString(3, phnum);
		p.setString(4, aphnum);
		p.setString(5, cemail);
		p.setString(6, desig);
		p.setInt(7, salary);
		int res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Successfully registered");
			String username=fname.substring(0,2)+lname.substring(0,2)+phnum.substring(6);
			String password=desig.substring(0,2)+fname.substring(0,2)+lname.substring(0,2)+phnum.substring(0,2);
			String q="select eid from registerEmployee where firstName='"+fname+"' and lastName='"+lname+"'";
			p=c.prepareStatement(q);
			rs=p.executeQuery();
			int eid=0;
			if(rs.next())
			{
				eid=rs.getInt("eid");
			}
			p=c.prepareStatement("insert into credentials (eid,username,password) values(?,?,?)");
			p.setInt(1, eid);
			p.setString(2, username);
			p.setString(3, password);
			 res=p.executeUpdate();
			 if(res>0)
			 {
				 System.out.println("Credentials generated");
			 }
		}
		else
		{
			System.out.println("Failed to register");
		}
	}
	public static void updateEmployee(Connection c,PreparedStatement p,Scanner sc,ResultSet rs) throws Exception
	{
	System.out.println("Which record do you want to update? Enter employee id :");
	int eid=sc.nextInt();
	p=c.prepareStatement("select eid from registerEmployee where eid="+eid);
	rs=p.executeQuery();
	int id;
	if(rs.next())
//		 id=rs.getInt("eid");
	System.out.println("What do you want to update?\n1.FirstName\n2.LastName\n3.phnum\n4.alternative phnumber\n5.Entire row\n6.exit");
	int opinoin=sc.nextInt();
	sc.nextLine();
	switch(opinoin)
	{
	case 1:
		System.out.println("Enter firstname to update :");
		String fname=sc.nextLine();
		p=c.prepareStatement("update registerEmployee set firstName='"+fname+"' where eid="+eid);
		int res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Updated");
		}
		else	
		{
			System.out.println("Failed to update");
		}
		break;
	case 2:
		System.out.println("Enter lastname to update :");
		String lname=sc.nextLine();
		p=c.prepareStatement("update registerEmployee set lastName='"+lname+"' where eid="+eid);
		res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Updated");
		}
		else
		{
			System.out.println("Failed to update");
		}
		break;
	case 3:
		System.out.println("Enter mobile number to update :");
		String phnum=sc.nextLine();
		p=c.prepareStatement("update registerEmployee set phno='"+phnum+"' where eid="+eid);
	    res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Updated");
		}
		else
		{
			System.out.println("Failed to update");
		}
		break;
	case 4:
		System.out.println("Enter alternative mobile number to update :");
		String alphnum=sc.nextLine();
		p=c.prepareStatement("update registerEmployee set alternativephnum='"+alphnum+"' where eid="+eid);
	    res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Updated");
		}
		else
		{
			System.out.println("Failed to update");
		}
		break;
	case 5:
		System.out.println("Enter firstname to update :");
		fname=sc.nextLine();
		System.out.println("Enter lastname to update :");
		lname=sc.nextLine();
		System.out.println("Enter mobile number to update :");
		phnum=sc.nextLine();
		System.out.println("Enter alternative mobile number to update :");
		alphnum=sc.nextLine();
		p=c.prepareStatement("update registerEmployee set firstName=?,lastName=?,phno=?,alternativephnum=? where eid="+eid);
		p.setString(1, fname);
		p.setString(2, lname);
		p.setString(1, phnum);
		p.setString(4, alphnum);
	    res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Updated");
		}
		else
		{
			System.out.println("Failed to update");
		}
		break;
	case 6:
		System.out.println("Exiting..");
		System.exit(0);
	}
	}
	public static void retriveEmployee(Connection c,PreparedStatement p,Scanner sc,ResultSet rs) throws Exception
	{
	System.out.println("Choose options:\n1.Retrieve particular record\n2.Entire table records");
	int opinion=sc.nextInt();
	switch(opinion)
	{
	case 1:
		System.out.println("Enter employee id to retrieve:");
		int id=sc.nextInt();
		p=c.prepareStatement("select * from registerEmployee where eid="+id);
		rs=p.executeQuery();
		ResultSetMetaData rsm=rs.getMetaData();
		if(rs.next()&& rs.getInt("eid")==id)
		{
			int count=rsm.getColumnCount();
			for(int i=1;i<=count;i++)
			{
				System.out.print(rs.getString(i)+" ");
			}
		}
		else
		{
			System.out.println("Id not exists with that");
		}
		break;
	case 2:
		p=c.prepareStatement("select * from registerEmployee");
		rs=p.executeQuery();
		 rsm=rs.getMetaData();
		 while(rs.next())
		 {
			 int count=rsm.getColumnCount();
				for(int i=1;i<=count;i++)
				{
					System.out.print(rs.getString(i)+" ");
				}
		 }
		break;
	}
	}
	public static void deleteEmployee(Connection c,PreparedStatement p,Scanner sc,ResultSet rs) throws Exception
	{
		System.out.println("Enter employee id to delete:");
		int id=sc.nextInt();
		p=c.prepareStatement("delete from registerEmployee where eid="+id);
	    int res=p.executeUpdate();
	    if(res>0)
	    {
	    	System.out.println("Deleted");
	    }
	    else
	    {
	    	System.out.println("No such record with that id.");
	    }
	}
	public static void trainerAddEmployee(Connection c,PreparedStatement p,Scanner sc,ResultSet rs) throws Exception
	{
		p=c.prepareStatement("insert into registerEmployee(firstName,lastName,phno,alternativephnum,cemail,designation,salary) values(?,?,?,?,?,?,?)");
	    sc.nextLine();
		System.out.println("Enter firstname:");
		String fname=sc.nextLine();
		System.out.println("Enter lastname:");
		String lname=sc.nextLine();
		System.out.println("Enter phnum:");
		String phnum=sc.nextLine();
		System.out.println("Enter alternative phnum:");
		String aphnum=sc.nextLine();
		
		String cemail=fname.substring(0,1)+lname+"@miraclesoft.com";
		System.out.println("Enter designation:");
		String desig=sc.nextLine();
		System.out.println("Enter salary:");
		int salary=sc.nextInt();
		p.setString(1, fname);
		p.setString(2, lname);
		p.setString(3, phnum);
		p.setString(4, aphnum);
		p.setString(5, cemail);
		p.setString(6, desig);
		p.setInt(7, salary);
		if(!desig.equalsIgnoreCase("hr"))
		{
		int res=p.executeUpdate();
		if(res>0)
		{
			System.out.println("Successfully registered");
			String username=fname.substring(0,2)+lname.substring(0,2)+phnum.substring(6);
			String password=desig.substring(0,2)+fname.substring(0,2)+lname.substring(0,2)+phnum.substring(0,2);
			String q="select eid from registerEmployee where firstName='"+fname+"' and lastName='"+lname+"'";
			p=c.prepareStatement(q);
			rs=p.executeQuery();
			int eid=0;
			if(rs.next())
			{
				eid=rs.getInt("eid");
			}
			p=c.prepareStatement("insert into credentials (eid,username,password) values(?,?,?)");
			p.setInt(1, eid);
			p.setString(2, username);
			p.setString(3, password);
			 res=p.executeUpdate();
			 if(res>0)
			 {
				 System.out.println("Credentials generated");
			 }
		}
		else
		{
			System.out.println("Failed to register");
		}
		}
		else
		{
			throw new MyException("You are not authorized to add Hr");
		}
	}
	public static void trainerRetriveEmployee(Connection c,PreparedStatement p,Scanner sc,ResultSet rs) throws Exception
	{
		System.out.println("Choose options:\n1.Retrieve particular record\n2.Entire table records");
		int opinion=sc.nextInt();
		switch(opinion)
		{
		case 1:
			System.out.println("Enter employee id to retrieve:");
			int id=sc.nextInt();
			p=c.prepareStatement("select r.eid,r.firstName,r.lastName,r.phno,r.alternativephnum,r.cemail,r.designation,c.username from registerEmployee r inner join credentials c on r.eid="+id);
			rs=p.executeQuery();
			ResultSetMetaData rsm=rs.getMetaData();
			if(rs.next()&& rs.getInt("eid")==id)
			{
				int count=rsm.getColumnCount();
				for(int i=1;i<=count;i++)
				{
					System.out.print(rs.getString(i)+" ");
				}
			}
			else
			{
				System.out.println("Id not exists with that");
			}
			break;
		case 2:
			p=c.prepareStatement("select r.eid,r.firstName,r.lastName,r.phno,r.alternativephnum,r.cemail,r.designation,c.username from registerEmployee r inner join credentials c on r.eid=c.eid");
			rs=p.executeQuery();
			 rsm=rs.getMetaData();
			 while(rs.next())
			 {
				 int count=rsm.getColumnCount();
					for(int i=1;i<=count;i++)
					{
						System.out.print(rs.getString(i)+" ");
					}
					System.out.println();
			 }
			break;
			default:
				throw new MyException("Invalid Choice");
		}
	}
	public static void updateCredentialsSelf(Connection c,PreparedStatement p,Scanner sc,ResultSet rs,int eid) throws Exception
	{
		System.out.println("What do you want to update?\n1.username\n2.password");
		int opinion=sc.nextInt();
		sc.nextLine();
		switch(opinion)
		{
		case 1:
			System.out.println("Enter new username");
			String user=sc.nextLine();
			p=c.prepareStatement("update credentials set username='"+user+"' where eid="+eid);
			int res=p.executeUpdate();
			if(res>0)
			{
				System.out.println("Username updated");
				System.out.println("Do you want to change the password?[yes/no]");
				String opt=sc.nextLine();
				if(opt.equalsIgnoreCase("yes"))
				{
					System.out.println("Enter new password");
					String p1=sc.nextLine();
					System.out.println("confirm password");
					String p2=sc.nextLine();
					if(p1.equals(p2))
					{
						p=c.prepareStatement("update credentials set password='"+p1+"' where eid="+eid);
						 res=p.executeUpdate();
						 if(res>0)
						 {
							 System.out.println("password changed");
						 }
						 else
						 {
							 System.out.println("failed to change");
						 }
					}
					else
					{
						System.out.println("Password mismatch..!");
					}
				}
				else
				{
					System.out.println("Exiting..");
				}
			}
			break;
		case 2:
			System.out.println("Enter new password");
			String p1=sc.nextLine();
			System.out.println("confirm password");
			String p2=sc.nextLine();
			if(p1.equals(p2))
			{
				p=c.prepareStatement("update credentials set password='"+p1+"' where eid="+eid);
				 res=p.executeUpdate();
				 if(res>0)
				 {
					 System.out.println("password changed");
				 }
				 else
				 {
					 System.out.println("failed to change");
				 }
			}
			else
			{
				System.out.println("Password mismatch..!");
			}
			break;
			default:
				throw new MyException("Invalid choice");
		}
	}

public static void main(String[] args)throws Exception {
	Properties p=new Properties();
	FileInputStream f=new FileInputStream("./mysql.properties");
	p.load(f);
	String url="jdbc:mysql://localhost:3306/EmployeeRegistration";
	String user=p.getProperty("username");
	String pass=p.getProperty("password");
	Connection con=null;
	Scanner sc=new Scanner(System.in);
	Statement s=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	try
	{
		con=DriverManager.getConnection(url,user,pass);
		System.out.println("Enter username:");
		String username=sc.nextLine();
		String q="select username from credentials where username='"+username+"'";
		s=con.createStatement();
		rs=s.executeQuery(q);
		if(rs.next()&&rs.getString("username").equals(username))
		{
			int count=0;
			while(count<3)
			{
		System.out.println("Enter password:");
		String password=sc.nextLine();
		 q="select password from credentials where username='"+username+"' and password='"+password+"'";
		 s=con.createStatement();
			rs=s.executeQuery(q);
			if(rs.next()&&rs.getString("password").equals(password))
			{
				q="select eid from credentials where username='"+username+"' and password='"+password+"'";
				s=con.createStatement();
				rs=s.executeQuery(q);
				if(rs.next())
				{
					int eid=rs.getInt("eid");
					q="select designation from registerEmployee where eid="+eid;
					s=con.createStatement();
					rs=s.executeQuery(q);
					if(rs.next())
					{
						String desg=rs.getString("designation").toLowerCase();
						while(true)
						{
							System.out.println();
						switch(desg)
						{
						case "hr":
							System.out.println("choose choice:\n1.AddEmployee"
									+ "\n2.updateEmployee"
									+ "\n3.retrieveEmployees"
									+ "\n4.deleteEmployee"
									+ "\n5.Update credentials"
									+ "\n6.exit");
							int opt=sc.nextInt();
							switch(opt)
							{
							case 1:
								addEmployee(con,ps,sc,rs);
								break;
							case 2:
								updateEmployee(con,ps,sc,rs);
								break;
							case 3:
								retriveEmployee(con,ps,sc,rs);
								break;
							case 4:
								deleteEmployee(con,ps,sc,rs);
								break;
							case 5:
								updateCredentialsSelf(con,ps,sc,rs,eid);
								break;
							case 6:
								System.out.println("logging out");
								System.exit(0);
								default:
									throw new MyException("Invalid choice");
							}
							break;
						case "trainer":
							System.out.println("choose choice:"
									+ "\n1.AddEmployee"
									+ "\n2.retrieveEmployees"
									+ "\n3.Update credentials"
									+ "\n4.exit");
							 opt=sc.nextInt();
							switch(opt)
							{
							case 1:
								trainerAddEmployee(con,ps,sc,rs);
								break;
							case 2:
								trainerRetriveEmployee(con,ps,sc,rs);
								break;
							case 3:
								updateCredentialsSelf(con,ps,sc,rs,eid);
								break;
							case 4:
								System.out.println("logging out");
								System.exit(0);
								default:
									throw new MyException("Invalid choice");
							}
							break;
						case "trainee":
							System.out.println("choose choice:"
									+ "\n1.retrieveEmployees"
									+ "\n2.Update credentials"
									+ "\n3.exit");
							 opt=sc.nextInt();
							switch(opt)
							{
							case 1:
								trainerRetriveEmployee(con,ps,sc,rs);
								break;
							case 2:
								updateCredentialsSelf(con,ps,sc,rs,eid);
								break;
							case 3:
								System.out.println("logging out");
								System.exit(0);
								default:
									throw new MyException("Invalid choice");
							}
							break;
					}
				}
				}
			}
				break;
			}
			else
			{
			count++;
			if(count>3)
			{
			throw new MyException("Oops something went wrong..Please try later");
			}
			else 
			{
				System.out.println("Password mismatch try again..");
			}
			}
		}
	}
		else
		{
			throw new MyException("User not exits");
		}
	}
	catch (MyException e) {
	System.out.println(e);	
	}
	finally 
	{
		if(ps!=null)
		{
			ps.close();
		}
		if(s!=null)
		{
			s.close();
		}
		if(rs!=null)
		{
			rs.close();
		}
		if(con!=null)
		{
			con.close();
		}
	}
}
}

// #Dataset

// create database EmployeeRegistration;
// use EmployeeRegistration;
// create table role(rid int primary key auto_increment,
// role varchar(10) unique);
// insert into role(role) values('hr');
// insert into role(role) values('trainee');
// select * from role;

// create table registerEmployee(eid int primary key auto_increment,
// firstName varchar(50),lastName varchar(50),phno varchar(10) not null, alternativephnum varchar(10),
// cemail varchar(100) unique,designation varchar(15),salary decimal(10,2));
// select * from registerEmployee;

// create table credentials(uid int primary key auto_increment,
// eid int,username varchar(20),password varchar(20),
// foreign key(eid)references registerEmployee(eid));
// select * from credentials;
