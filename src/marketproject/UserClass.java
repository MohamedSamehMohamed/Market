package marketproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class UserClass {

    private String Email;
    private String Password;
    private String FName;
    private String LName;
    private String DateofBirth;
    private String DateOfRegister;
    private int Age[] = new int[3];
    private int Money;
    private boolean Login;
    private String LoginQ = "select * from users where ";
    private String[] Available = {"@gmail.com", "@yahoo.com", "@hotmal.com"};
    private String Forbidden = "!@#$%^&*()+=/?\\\"';: ";

    boolean SetEmail(String Email, boolean login) throws ClassNotFoundException, SQLException {
        while (Email.length() != 0 && Email.endsWith(" ")) {
            Email = Email.substring(0, Email.length() - 1);
        }
        if (Email.length() < 11 || Email.length() > 250) {
            return false;
        }
        int suffid = -1;
        for (int i = 0; i < Available.length; i++) {

            if (Email.endsWith(Available[i])) {
                suffid = i;
                break;
            }
        }
        if (suffid == -1) {
            return false;
        }
        for (int i = 0; i < Email.length() - Available[suffid].length(); i++) {
            for (int j = 0; j < Forbidden.length(); j++) {
                if (Email.charAt(i) == Forbidden.charAt(j)) {
                    return false;
                }
            }
        }
        if (!login) {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
                System.out.println("Connection is Alive");
                Statement statement = con.createStatement();
                String curQ = "select * from users where Email = '" + Email + "';";
                ResultSet ret = statement.executeQuery(curQ);
                if (ret.first()) {
                    return false;
                }
                this.Email = Email;
                return true;
            }
        }

        return true;
    }

    void SETEMAIL(String Email) {
        this.Email = Email;
    }

    boolean SetPassword(String Password) throws Exception {
        if (Password.length() < 10) {
            return false;
        }
        String c = "'";
        for (int i = 0; i < Password.length(); i++) {
            // if (Password.charAt(i) == c.charAt(0))throw new Exception("Password Can't be Set ! ");
        }
        this.Password = Password;
        return true;
    }

    boolean SetFLName(String FName, String LName) throws Exception {
        if (FName.length() == 0 || LName.length() == 0 )throw new Exception("Name is null "); 
        String Tem = FName + LName;
        for (int i = 0; i < Tem.length(); i++) {
            for (int j = 0; j < Forbidden.length(); j++) {
                if (Tem.charAt(i) == Forbidden.charAt(j)) {
                    throw new Exception("Name Not Correct ");
                }
            }
        }
        this.FName = FName;
        this.LName = LName;
        return true;
    }

    boolean SetDateOfBirth(String DateOfBirth) throws Exception {
        this.DateofBirth = DateOfBirth;
        
        if (DateofBirth.length() == 0)throw new Exception("Age is Null"); 
        
        SetAge();
        return true;
    }

    boolean SetMoney(String Money) {
        this.Money = Integer.parseInt(Money);
        if (this.Money < 0) {
            return false;
        }
        return true;
    }

    String getEmail() {
        return Email;
    }

    String getFName() {
        return FName;
    }

    String getLName() {
        return LName;
    }

    String getPassword() {
        return Password;
    }

    int getMoney() {
        return Money;
    }

    boolean IsLogin() {
        return Login;
    }

    public UserClass() {
        Login = false;
        Email = Password = FName = LName = DateOfRegister = DateofBirth = "";
        Money = 0;
    }

    String InsertStatement() {
        String ret = "insert into users (Email , password , FName , Lname , DateofBirth , AmountOfMony )values(? , ? , ? , ? , ? , ? )";
        return ret;
    }

    boolean RegisterUser() throws ClassNotFoundException, SQLException {
        System.out.println("Register Start...");
        return TryToRegister();
    }

    private boolean TryToRegister() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive");
            PreparedStatement statement = con.prepareStatement(InsertStatement());
            //Email , password , FName , Lname , BirthOfDate , AmountOfMony

            statement.setString(1, Email);
            statement.setString(2, Password);
            statement.setString(3, FName);
            statement.setString(4, LName);
            statement.setString(5, DateofBirth);
            statement.setInt(6, Money);

            try {
                statement.executeUpdate();
            } catch (SQLException E) {
                // Won't Ouccr
                System.out.println(E.getMessage());
                Login = false;
                return false;
            }
            Login = true;
            return true;
        }
    }

    boolean LoginUser(String Email, String Password) throws ClassNotFoundException, SQLException, Exception {
        if (!SetEmail(Email, true)) {
            return false;
        }
        if (!SetPassword(Password)) {
            return false;
        }
        System.out.println("Done Check Validation of Email And Password ");
        return TryToLogIn(Email, Password);
    }

    private boolean TryToLogIn(String Email, String Password) throws ClassNotFoundException, SQLException, Exception {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive IN Login");

            Statement statement = con.createStatement();
            String curQ = LoginQ + "Email = '" + Email + "' and Password = '" + Password + "';";
            ResultSet ret = statement.executeQuery(curQ);
            if (!ret.first()) {
                //System.out.println("Wrong Date Input");
                return false;
            }
            this.Email = Email;
            this.Password = Password;
            FName = ret.getString("FName");
            LName = ret.getString("LName");
            DateofBirth = ret.getString("DateOfBirth");
            Money = ret.getInt("AmountOFMony");
            DateOfRegister = ret.getString("DateOfRegister");
            SetAge();
            Login = true;
            return true;
        }
    }

    void Update() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive IN Login");

            Statement statement = con.createStatement();
            String curQ = "update users set FName = '" + FName + "' , LName = '" + LName + "' , Password = '" + Password + "' , AmountOFMony = " + Money + " where Email = '" + Email + "';";
            System.out.println(curQ);
            statement.executeUpdate(curQ);
            curQ = "update products set FName = '" + FName + "' where Email = '" + Email + "';";
            statement.executeUpdate(curQ);
        }
    }

    void UpdateMoney(int extra) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive IN Login");

            Statement statement = con.createStatement();
            String curQ = "update users set AmountOFMony =  AmountOFMony + " + extra + " where Email = '" + Email + "';";
            System.out.println(curQ);
            statement.executeUpdate(curQ);
        }
    }

    String getAge() {
        String ret = Age[0] + " Year" + (Age[0] > 1 ? "s ," : " ,") + Age[1] + " Month" + (Age[1] > 1 ? "s and" : " and") + Age[2] + " Day" + (Age[2] > 1 ? "s ." : " .");
        return ret;
    }

    void SetAge() throws Exception {
        String Tem[] = new String[3];
        String Tem1[] = new String[3];
        int Old[] = new int[3];
        int New[] = new int[3];
        int Factor[] = {0, 12, 30};
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");//"yyyy/MM/dd HH:mm:ss"  
        LocalDateTime now = LocalDateTime.now();
        String CurrentDate = dtf.format(now);

        int i = -1;
        for (int j = 0; j < 3; j++) {
            Tem[j] = Tem1[j] = "";
            for (i++; i < DateofBirth.length() && DateofBirth.charAt(i) != '-'; i++) {
                Tem[j] += DateofBirth.charAt(i);
                Tem1[j] += CurrentDate.charAt(i);
            }
            Old[j] = Integer.parseInt(Tem[j]);
            New[j] = Integer.parseInt(Tem1[j]);
        }
        for (i = 2; i >= 0; i--) {
            int current = New[i] - Old[i];
            if (current >= 0) {
                Age[i] = current;
            } else {
                New[i - 1]--;
                New[i] += Factor[i];
                i++;
            }
        }
        //  for ( i =0 ; i < 3 ;i++)if (Age[i] < 0 )throw new Exception ("You Came From The Future. . .");

    }

}
