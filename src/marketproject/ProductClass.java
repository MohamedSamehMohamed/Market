package marketproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductClass {

    private int Id;
    private String Phurl;
    private String FName;
    private String Email;
    private String ProductName;
    private int Price;
    private int Number;
    private String DateOfAdded;

    public ProductClass() {
        Id = Price = Number = 0;
        Phurl = FName = ProductName = DateOfAdded = Email = "";
    }

    void SetId(int Id) {
        this.Id = Id;
    }

    void SetPhurl(String Phurl) throws Exception {
        if (Phurl == null || Phurl.equals("")) {
            throw new Exception("Path of Photo is null ");
        }
        this.Phurl = Phurl;
    }

    void SetFName(String FName) {
        this.FName = FName;
    }

    void SetEmail(String Email) {
        this.Email = Email;
    }

    void SetProductName(String ProdutName) throws Exception {
        if (ProdutName == null || ProdutName.equals("")) {
            throw new Exception("Product Name is Null ");
        }
        this.ProductName = ProdutName;
    }

    void SetPriceAndNumber(int Price, int Number) throws Exception {
        if (Price <= 0) {
            throw new Exception("Price Must Be Positive ");
        }
        this.Price = Price;
        this.Number = Number;
    }

    void SetDate(String DateOfAdded) {
        this.DateOfAdded = DateOfAdded;
    }

    void InsertProduct() throws ClassNotFoundException, SQLException {
        String InsertQ = "insert into products  (Phurl , FName , Email , Name , Price , Number) values ( ? , ? , ? , ? , ? , ? ) ;";
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive");
            PreparedStatement statement = con.prepareStatement(InsertQ);

            statement.setString(1, Phurl);
            statement.setString(2, FName);
            statement.setString(3, Email);
            statement.setString(4, ProductName);
            statement.setInt(5, Price);
            statement.setInt(6, Number);
            try {
                statement.executeUpdate();
                System.out.println("Done !");
            } catch (SQLException E) {
                // Won't Ouccr
                System.out.println(E.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return ProductName;
    }

    public String[] GetInfo() {
        String ret[] = {": " + FName, ": " + ProductName, ": " + Integer.toString(Price), ": " + Integer.toString(Number)};
        return ret;
    }

    boolean Update() throws ClassNotFoundException, SQLException {
        String UpdateQ = "update products set Number = " + Number + " where Id = " + Id + ";";
        System.out.println(UpdateQ);
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive");
            Statement state = con.createStatement();
            state.executeUpdate(UpdateQ);
            UpdateQ = "delete from Products where Number = 0 ;";
            return (state.executeUpdate(UpdateQ) == 1 ? true : false);
        }
    }

    int getNumber() {
        return Number;
    }

    String getPhurl() {
        return Phurl;
    }

    int getPrice() {
        return Price;
    }

    String getProductName() {
        return ProductName;
    }

    String getEmail() {
        return Email;
    }

    String getName() {
        return FName;
    }
}
