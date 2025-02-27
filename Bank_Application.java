import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

class MyException extends Exception
{
MyException(String s)
{
super(s);
}
}
public class Bank {
public static void withdraw(Connection c,PreparedStatement p,Scanner sc,ResultSet rs, String acc) throws Exception
{
System.out.println("Enter money to withdraw:");
int withdraw=sc.nextInt();
p=c.prepareStatement("select balance from accountholders where accountnumber='"+acc+"'");
rs=p.executeQuery();
if(rs.next())
{
int bal=rs.getInt("balance");
if(withdraw>bal)
{
throw new MyException("Insufficient funds");
}
else
{
int update=bal-withdraw;
p=c.prepareStatement("update accountholders set balance=? where accountnumber=?");
p.setInt(1, update);
p.setString(2, acc);
int res=p.executeUpdate();
if(res>0)
{
p=c.prepareStatement("insert into transactions(accountnumber,status,withdrawed) values(?,?,?)");
p.setString(1, acc);
p.setString(2, "Debited");
p.setInt(3, withdraw);
res=p.executeUpdate();
System.out.println(withdraw+" debited from"+acc+" \n remaining balance is"+update);
}
else
{
System.out.println("Failed to update");
}
}
}
}
public static void deposit(Connection c,PreparedStatement p,Scanner sc,ResultSet rs, String acc) throws Exception
{
System.out.println("Enter money to deposit:");
int deposit=sc.nextInt();
p=c.prepareStatement("select balance from accountholders where accountnumber='"+acc+"'");
rs=p.executeQuery();
if(rs.next())
{
int bal=rs.getInt("balance");
if(deposit<500)
{
throw new Exception("Please deposit more than 500");
}
else
{
int update=bal+deposit;
p=c.prepareStatement("update accountholders set balance=? where accountnumber=?");
p.setInt(1, update);
p.setString(2, acc);
int res=p.executeUpdate();
if(res>0)
{
p=c.prepareStatement("insert into transactions(accountnumber,status,deposit) values(?,?,?)");
p.setString(1, acc);
p.setString(2, "credited");
p.setInt(3, deposit);
res=p.executeUpdate();
System.out.println(deposit+" credited to "+acc+" \ncurrent balance is "+update);
}
else
{
System.out.println("Failed to update");
}
}
}
}
public static void transfer(Connection c,PreparedStatement p,Scanner sc,ResultSet rs, String acc) throws Exception
{
sc.nextLine();
System.out.println("Enter account number to transfer:");
String rac=sc.nextLine();
p=c.prepareStatement("select * from accountholders where accountnumber='"+rac+"'");
rs=p.executeQuery();
if(rs.next())
{
System.out.println("Enter money to transfer:");
int transfer=sc.nextInt();
p=c.prepareStatement("select balance from accountholders where accountnumber='"+acc+"'");
rs=p.executeQuery();
if(rs.next())
{
int bal=rs.getInt("balance");
if(transfer>bal)
{
throw new Exception("Insufficient funds");
}
else
{
int updatedbal1=bal-transfer;
p=c.prepareStatement("update accountholders set balance=? where accountnumber=?");
p.setInt(1, updatedbal1);
p.setString(2, acc);
int res=p.executeUpdate();
p=c.prepareStatement("select balance from accountholders where accountnumber='"+rac+"'");
rs=p.executeQuery();
if(rs.next())
{
bal=rs.getInt("balance");
int updatedbal=bal+transfer;
p=c.prepareStatement("update accountholders set balance=? where accountnumber=?");
p.setInt(1, updatedbal);
p.setString(2, rac);
res=p.executeUpdate();
if(res>0)
{
p=c.prepareStatement("insert into transactions(accountnumber,status,deposit) values(?,?,?)");
p.setString(1, rac);
p.setString(2, "credited");
p.setInt(3, transfer);
res=p.executeUpdate();
System.out.println(transfer+" credited to "+rac+" \ncurrent balance is "+updatedbal);
p=c.prepareStatement("insert into transfer (sender_accountnumber,receiver_accountnumber,description) values(?,?,?)");
p.setString(1, acc);
p.setString(2, rac);
String des=transfer+" debited from "+acc+" credited to "+rac;
p.setString(3, des);
p.executeUpdate();
}
}
if(res>0)
{
p=c.prepareStatement("insert into transactions(accountnumber,status,withdrawed) values(?,?,?)");
p.setString(1, acc);
p.setString(2, "debited");
p.setInt(3, transfer);
res=p.executeUpdate();
System.out.println(transfer+" debited from "+acc+" \nRemaining balance is "+updatedbal1);
}
else
{
System.out.println("Failed to update");
}
}
}
}
else
{
throw new MyException("receiver account not found");
}
}
public static void fetch(Connection c,PreparedStatement p,Scanner sc,ResultSet rs, String acc) throws Exception
{
System.out.println("Choose option:\n1.Retrieve overall transactions"
+ "\n2.Retrieve withdraws"
+ "\n3.Retrieve data about money credits"
+ "\n4.exit");
int choice=sc.nextInt();
switch (choice) {
case 1:
p=c.prepareStatement("select accountnumber,withdrawed,deposit,status,Transaction_time from transactions where accountnumber='"+acc+"'");
rs=p.executeQuery();
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
break;
case 2:
p=c.prepareStatement("select accountnumber,withdrawed,status,Transaction_time from transactions where accountnumber='"+acc+"' and withdrawed!=0.00");
rs=p.executeQuery();
rsm=rs.getMetaData();
count=rsm.getColumnCount();
while(rs.next())
{
for(int i=1;i<=count;i++)
{
System.out.print(rs.getString(i)+" ");
}
System.out.println();
}
break;
case 3:
p=c.prepareStatement("select accountnumber,deposit,status,Transaction_time from transactions where accountnumber='"+acc+"' and deposit!=0.00");
rs=p.executeQuery();
rsm=rs.getMetaData();
count=rsm.getColumnCount();
while(rs.next())
{
for(int i=1;i<=count;i++)
{
System.out.print(rs.getString(i)+" ");
}
System.out.println();
}
break;
default:
throw new MyException("Invalid choice: " + choice);
}
}
public static void main(String[] args) throws Exception {
Properties p=new Properties();
FileInputStream f=new FileInputStream("./mysql.properties");
p.load(f);
String url=p.getProperty("url");
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
System.out.println("Enter acc number:");
String accno=sc.nextLine();
s=con.createStatement();
rs=s.executeQuery("select count(*) as count from accountholders where accountnumber='"+accno+"'");
if(rs.next()&&rs.getInt("count")>0)
{
int count1=0;
while(count1<3)
{

System.out.println("Enter password:");
String password=sc.nextLine();
s=con.createStatement();
rs=s.executeQuery("select password from accountholders where accountnumber='"+accno+"'and password='"+password+"'");
if(rs.next()&&rs.getString("password").equals(password))
{
ps=con.prepareStatement("insert into logindetails(accountnumber,status) values(?,?)");
ps.setString(1, accno);
ps.setString(2,"Login success");
int res=ps.executeUpdate();
if(res>0)
{
System.out.println("Login status updated");
}
else
{
System.out.println("Failed to update");
}
System.out.println("Choose your choice:\n"
+ "1.withdraw \n"
+ "2.Deposit\n"
+ "3.Transfer\n"
+ "4.Fetch the details about transactions\n"
+ "5.exit\n"
+" please choose any option from above.");
int option=sc.nextInt();
switch(option)
{
case 1:
withdraw(con,ps,sc,rs,accno);
break;
case 2:
deposit(con,ps,sc,rs,accno);
break;
case 3:
transfer(con,ps,sc,rs,accno);
break;
case 4:
fetch(con,ps,sc,rs,accno);
break;
case 5:
System.out.println("Exiting..");
System.exit(0);
default:
System.out.println("Invalid choice..!");
}
break;
}
else
{
count1++;
ps=con.prepareStatement("insert into logindetails(accountnumber,status) values(?,?)");
ps.setString(1, accno);
ps.setString(2,"Login failed");
int res=ps.executeUpdate();
if(res>0)
{
System.out.println("Login status updated");
}
else
{
System.out.println("Failed to update");
}
if(count1>3)
{
throw new MyException("Oops something went wrong..");
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
throw new MyException("Not exists");
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

// #DataSet

// create database Bank;
// create table accountholders(
// aid int auto_increment primary key,accountnumber varchar(20) unique not null,
// fname varchar(50),lname varchar(50),email varchar(100) unique,balance decimal(15,2) not null,
//  phnumber varchar(10),password varchar(20));
// drop table accountholders;
// select * from accountholders;

// create table transactions(tid int auto_increment primary key,accountnumber varchar(20),
// withdrawed decimal(10,2) default 0,debited decimal(10,2) default 0,status varchar(50),Transaction_time datetime default current_timestamp,
// foreign key(accountnumber) references accountholders(accountnumber) );
// alter table transactions rename column debited to deposit ;
// drop table transactions;
// select * from transactions;
// use JDBCTask;
// create table logindetails(lid int primary key auto_increment,
// accountnumber varchar(20), status varchar(100),Logined datetime default current_timestamp,foreign key(accountnumber)
// references accountholders(accountnumber));
// select * from logindetails;
// drop table logindetails;

// create table transfer(tid int primary key auto_increment,
// sender_accountnumber varchar(20) not null,receiver_accountnumber varchar(20) not null,
// transaction_time datetime default current_timestamp);
// alter table transfer add column description varchar(200);
// select * from transfer;

	
