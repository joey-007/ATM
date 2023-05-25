import java.sql.*;
import java.util.Scanner;

public class atm1 {
    public static void main(String[] args) throws Exception {
        atm1 a=new atm1();
        Scanner sc=new  Scanner(System.in);
        while(true){
        System.out.println("Enter 1 for Creating an Account");
        System.out.println("Enter 2 for Withdrawl");
        System.out.println("Enter 3 for Deposit");
        System.out.println("Enter 4 for Transfer");
        System.out.println("Enter 5 for Transactions");
        System.out.println("Enter 6 for exit");
        int choice=sc.nextInt();
        switch (choice){
            case 1:a.createaccount();break;
            case 2:a.withdraw();break;
            case 3:a.deposit();break;
            case 4:a.transfer();break;
            case 5:a.transactions();break;
            case 6:
                System.out.println("See ya");
                System.exit(0);
            default:
                System.out.println("Enter valid number");
        }
        }
    }
    public void transactions() throws Exception{
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter username");
        String uname=sc.nextLine();
        System.out.println("Enter pin");
        int pin=sc.nextInt();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from data1 where uname='"+uname+"' and pin='"+pin+"'");
        if(rs.next()==true){
            System.out.println("The number of transactions are"+" "+rs.getInt(4));
        }
        rs.close();
        stmt.close();
        con.close();
    }
public void createaccount() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter username");
    String uname=sc.nextLine();
    System.out.println("Enter pin");
    int pin=sc.nextInt();
    System.out.println("Enter Balance");
    int bal=sc.nextInt();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
        PreparedStatement smt = con.prepareStatement("insert into data1 values(?,?,?,?)");
        smt.setString(1, uname);
        smt.setInt(2, pin);
        smt.setInt(3, bal);
        smt.setInt(4,1);
        smt.executeUpdate();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from data1 where uname='"+uname+"' and pin='"+pin+"'");
        if(rs.next()==true){
            System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
        }

            smt.close();
            con.close();


    } catch (Exception e) {
        System.out.println(e);
    }
}
public void withdraw() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter username");
    String uname=sc.nextLine();
    System.out.println("Enter pin");
    int pin=sc.nextInt();
    System.out.println("Enter money to withdraw");
    int mw=sc.nextInt();
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from data1 where uname='"+uname+"' and pin='"+pin+"'");
    if(rs.next()==true){
        if(mw>rs.getInt(3)){
            System.out.println("Insufficient Funds");
        }
        else{
            System.out.println("Successful Withdrawl");
            int new1 = rs.getInt(3)-mw;
            PreparedStatement smt = con.prepareStatement("update data1 set bal=?,tran=? where uname=? and pin=?");
            smt.setInt(1,new1);
            smt.setString(3,uname);
            smt.setInt(4,pin);
            smt.setInt(2,rs.getInt(4)+1);
            smt.executeUpdate();
            smt.close();
        }
    }
    rs.close();
    stmt.close();
    con.close();
}
public void deposit() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter username");
    String uname=sc.nextLine();
    System.out.println("Enter pin");
    int pin=sc.nextInt();
    System.out.println("Enter money to deposit");
    int md=sc.nextInt();
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from data1 where uname='"+uname+"' and pin='"+pin+"'");
    if(rs.next()==true){
        System.out.println("Successfully Deposited");
        int new1 = rs.getInt(3)+md;
        PreparedStatement smt = con.prepareStatement("update data1 set bal=?,tran=? where uname=? and pin=?");
        smt.setInt(1,new1);
        smt.setString(3,uname);
        smt.setInt(4,pin);
        smt.setInt(2,rs.getInt(4)+1);
        smt.executeUpdate();
        smt.close();
    }
    rs.close();
    stmt.close();
    con.close();
}

public void transfer() throws Exception{
    Scanner sc1=new Scanner(System.in);
    System.out.println("Enter username");
    String uname=sc1.nextLine();
    System.out.println("Enter pin");
    int pin=sc1.nextInt();
    sc1.nextLine();
    System.out.println("Enter username you want to transfer");
    String uname2=sc1.nextLine();
    System.out.println("Enter amount you want to transfer");
    int mt=sc1.nextInt();
    sc1.nextLine();
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from data1 where uname='"+uname+"' and pin='"+pin+"'");
    Statement stmt1 = con.createStatement();
    ResultSet rs1 = stmt1.executeQuery("select * from data1 where uname='"+uname2+"'");
    if(rs.next()==true && rs1.next()==true){
        if(mt>rs.getInt(3)){
            System.out.println("Insufficient Funds");
        }
        else{
            PreparedStatement smt = con.prepareStatement("update data1 set bal=?,tran=? where uname=? and pin=?");
            PreparedStatement smt1 = con.prepareStatement("update data1 set bal=?,tran=? where uname=?");
            smt.setInt(1,rs.getInt(3)-mt);
            smt.setString(3,uname);
            smt.setInt(4,pin);
            smt.setInt(2,rs.getInt(4)+1);
            smt.executeUpdate();
            smt1.setInt(1,rs1.getInt(3)+mt);
            smt1.setString(3,uname2);
            smt1.setInt(2,rs1.getInt(4)+1);
            smt1.executeUpdate();
            smt.close();
            smt1.close();
        }
        rs1.close();
        rs.close();
        stmt1.close();
        stmt.close();

        con.close();
    }
}
}
