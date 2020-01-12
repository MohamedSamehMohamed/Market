package marketproject;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MarketProject extends Application {

    Stage primaryStage;
    Stage regesteration;
    Stage login;
    Stage want;
    Stage sell;
    Stage buy;
    Stage sales;
    Stage Purchases;
    Stage about;
    int Id;
    UserClass User;

    boolean SetValues(String Email, String Password, LocalDate Date, String FN, String LN, String Money) throws ClassNotFoundException, SQLException, Exception {
        System.out.println("Start");
        String D = Date.toString();

        if (!User.SetEmail(Email, false)) {
            return false;
        }
        System.out.println("Email OK ");
        if (!User.SetPassword(Password)) {
            return false;
        }
        System.out.println("Password OK ");
        System.out.println(D);
        if (!User.SetDateOfBirth(D)) {
            return false;
        }
        System.out.println("Date Of Birth OK ");
        if (!User.SetFLName(FN, LN)) {
            return false;
        }
        System.out.println("Name OK ");
        if (!User.SetMoney(Money)) {
            return false;
        }
        System.out.println("Set Date Is Done OK ! ");

        return true;
    }

    void Registeriation() throws ClassNotFoundException, SQLException, Exception {

        if (User.RegisterUser()) {
            System.out.println("Welecome");
            regesteration.close();
            primaryStage.show();
        } else {
            System.out.println("Duplicated Data ");
        }
    }
    int NumberOfTries = 0;

    void Login(String Email, String Password) throws ClassNotFoundException, SQLException, Exception {

        User.LoginUser(Email, Password);
        if (!User.IsLogin()) {
            if (NumberOfTries == 2) {
                primaryStage.show();
                login.close();
                NumberOfTries = 0;
                throw new Exception("Can't Log in , May be You Are A Hacker and bruteforce the password :D \nNumber of tries left " + (3 - NumberOfTries));
                // Out From The Program  
            }
            NumberOfTries++;
            User = new UserClass();
            System.out.println("CAn't Log ");
            throw new Exception("Number of tries left " + (3 - NumberOfTries));

        }
        NumberOfTries = 0;
        System.out.println("Login Is Done!");
        login.close();
        need();
    }

    public void regest() {
        regesteration = new Stage();
        Label l21 = new Label("First Name ");
        Label l22 = new Label("last Name ");
        Label l23 = new Label("Amount of money ");
        Label l24 = new Label("date");
        Label l25 = new Label("Email : ");
        Label l26 = new Label("Password: ");
        TextField tf21 = new TextField();
        TextField tf22 = new TextField();
        TextField tf23 = new TextField();
        TextField tf24 = new TextField();
        TextField tf25 = new TextField();
        PasswordField ps = new PasswordField();
        DatePicker datePicker = new DatePicker();
        Button buttonRegister = new Button("Register");

        buttonRegister.setOnAction(e -> {
            try {

                if (!SetValues(tf25.getText(), ps.getText(), datePicker.getValue(), tf21.getText(), tf22.getText(), tf23.getText())) {
                    System.out.println("Faild");
                    return;
                }

                System.out.println("Registeration Start Now From Regster Button ");
                Registeriation();
            } catch (SQLException E) {
                System.out.println("Button Register ");
            } catch (ClassNotFoundException E) {
                System.out.println(E.getMessage());
            } catch (Exception E) {
                System.out.println(E.getMessage());
            }
        });
        GridPane g2 = new GridPane();
        g2.setVgap(5);
        g2.setHgap(5);
        g2.setAlignment(Pos.CENTER);
        //  g2.add(rl1, 0, 0); 
        //g2.add(rtf1, 1, 0); 

        g2.add(l21, 0, 0);
        g2.add(tf21, 1, 0);

        g2.add(l22, 0, 1);
        g2.add(tf22, 1, 1);
        g2.add(l23, 0, 2);
        g2.add(tf23, 1, 2);
        g2.add(l24, 0, 3);
        g2.add(datePicker, 1, 3);

        g2.add(l25, 0, 4);
        g2.add(tf25, 1, 4);

        g2.add(l26, 0, 5);
        g2.add(ps, 1, 5);

        g2.add(buttonRegister, 1, 7);

        //Styling nodes   
        buttonRegister.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");

        tf21.setStyle("-fx-font: normal bold 15px 'serif' ");
        tf22.setStyle("-fx-font: normal bold 15px 'serif' ");
        tf23.setStyle("-fx-font: normal bold 15px 'serif' ");
        tf24.setStyle("-fx-font: normal bold 15px 'serif' ");
        tf25.setStyle("-fx-font: normal bold 15px 'serif' ");
        g2.setPadding(new Insets(30, 10, 10, 10));

        g2.setStyle("-fx-background-color: #00CCCC;");

        Scene s2 = new Scene(g2, 500, 500);
        regesteration.setScene(s2);
        regesteration.setTitle("regesteration");
        regesteration.show();

    }

    public void log() {
        login = new Stage();
        GridPane p1 = new GridPane();
        p1.setHgap(10);
        p1.setVgap(10);
        p1.setPadding(new Insets(10, 10, 10, 10));
        Label l11 = new Label("Email: ");
        Label l12 = new Label("Password: ");
        Label Stautus = new Label();
        TextField tf11 = new TextField();
        PasswordField tf12 = new PasswordField();
        Button b11 = new Button("Login");
        b11.setOnAction(e -> {
            try {
                if (tf11.getText().length() == 0 || tf12.getText().length() == 0) {
                    tf11.setText("Fill The Empty ");

                } else {
                    Login(tf11.getText(), tf12.getText());
                }
            } catch (Exception E) {
                Stautus.setText(E.getMessage());
            }
        });
        Stautus.setMinWidth(50);
        p1.add(l11, 0, 5);
        p1.add(tf11, 1, 5);
        p1.add(l12, 0, 7);
        p1.add(tf12, 1, 7);
        p1.add(Stautus, 1, 10);
        p1.add(b11, 1, 9);
        p1.setHalignment(b11, HPos.CENTER);
        b11.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        tf11.setStyle("-fx-font: normal bold 20px 'serif' ");
        tf12.setStyle("-fx-font: normal bold 20px 'serif' ");
        p1.setStyle("-fx-background-color:#00CCCC ;");

        Scene s1 = new Scene(p1, 350, 350);
        login.setScene(s1);
        login.setTitle("login");
        login.show();

    }

    public void need() throws Exception {
        want = new Stage();
        BorderPane p3 = new BorderPane();

        Button b31 = new Button("buy");
        Button b32 = new Button("sell");
        Button b33 = new Button("about");
        Button b34 = new Button("Purchases");
        Button b35 = new Button("sales");
        Button b36 = new Button("Out");
        b31.setOnAction(e -> {
            want.close();
            try {
                buy();
            } catch (Exception ex) {
            }
        });
        b32.setOnAction(e -> {
            want.close();
            sell();
        });
        b33.setOnAction(e -> {
            want.close();
            getabout();

        });
        b34.setOnAction(e -> {
            want.close();
            try {
                getPurchases();
            } catch (Exception E) {

            }
        });
        b35.setOnAction(e -> {
            want.close();
            try {
                getsales();
            } catch (Exception E) {
            }
        });
        b36.setOnAction(e -> {
            want.close();
            primaryStage.show();
        });

        b31.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");
        b32.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");
        b33.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");
        b34.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");
        b35.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");
        b36.setStyle(
                "-fx-background-color: #177378; -fx-text-fill: white;");

        HBox hb3 = new HBox();
        hb3.setSpacing(15);
        //hb3.setAlignment(Pos.CENTER);
        hb3.setStyle("-fx-background-color:#00CCCC ");
        hb3.getChildren().addAll(b31, b32, b33, b34, b35, b36);
        ImageView img2 = new ImageView();
        try {
            img2 = new ImageView("mmm.jpg");
        } catch (IllegalArgumentException Ex) {
        }
        img2.setFitHeight(200);
        img2.setFitWidth(350);
        Text lb = new Text(100, 20, "welcome " + "  ");
        Font f = Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 20);
        lb.setFont(f);
        Pane pwant = new Pane();

        pwant.getChildren().add(lb);
        p3.setTop(pwant);
        p3.setCenter(img2);
        p3.setBottom(hb3);
        p3.setStyle("-fx-background-color: #00CCCC;");

        Scene s3 = new Scene(p3, 350, 350);
        want.setScene(s3);
        want.setTitle("what you need");
        want.show();

    }
///////

    public void sell() {
        sell = new Stage();
        GridPane p4 = new GridPane();
        // Label l41 = new Label("ID: ");
        //    Label l42 = new Label("Email: ");
        //    Label l43 = new Label("Name: ");
        Label Status = new Label();
        Label l44 = new Label("product: ");
        Label l45 = new Label("price: ");
        Label l46 = new Label("number: ");
        Label l47 = new Label("photo: ");
        //  TextField tf41 = new TextField();
        //TextField tf42 = new TextField();
        //TextField tf43 = new TextField("the name of owner");
        TextField tf44 = new TextField();
        TextField tf45 = new TextField();
        Label labpath = new Label();
        Button tf47 = new Button("Browse");
        ImageView img = new ImageView();

        tf47.setOnAction(e
                -> {

            FileChooser file = new FileChooser();
            file.setTitle("Open File");
            File F = file.showOpenDialog(sell);
            labpath.setText("file:" + F.getAbsolutePath());
            labpath.setMaxWidth(10);
            Image im = new Image(labpath.getText());
            img.setImage(im);
            img.setFitHeight(100);
            img.setFitWidth(100);
        });
        ComboBox<Integer> com = new ComboBox<>();

        for (int i = 1; i <= 100; i++) {
            com.getItems().add(i);
        }
        com.setValue(1);
        Button b41 = new Button("offer");
        b41.setOnAction(e -> {

            ProductClass Product = new ProductClass();
            try {
                Product.SetEmail(User.getEmail());
                Product.SetFName(User.getFName());
                Product.SetPhurl(labpath.getText());
                Product.SetProductName(tf44.getText());
                Product.SetPriceAndNumber(Integer.parseInt(tf45.getText()), com.getValue());
                Product.InsertProduct();

                Status.setText("Done!");

            } catch (Exception PE) {
                Status.setText("Faild : " + PE.getMessage());
            }
            labpath.setText("");
            tf44.setText("");
            tf45.setText("");
            com.setValue(1);
        });
        Button b42 = new Button("Back");
        b42.setOnAction(e -> {
            sell.close();
            want.show();
        });
        b41.setStyle(
                "-fx-background-color: green; -fx-textfill: white;");
        b42.setStyle(
                "-fx-background-color: green; -fx-textfill: white;");

        HBox hb4 = new HBox();
        hb4.setSpacing(20);
        hb4.setAlignment(Pos.CENTER);
        hb4.getChildren().addAll(b42, b41);
        p4.setVgap(5);
        p4.setHgap(5);
        p4.setAlignment(Pos.CENTER);
        p4.add(l44, 0, 0);
        p4.add(tf44, 1, 0);

        p4.add(l45, 0, 1);
        p4.add(tf45, 1, 1);

        p4.add(l46, 0, 2);
        p4.add(com, 1, 2);
        p4.add(l47, 0, 3);
        p4.add(tf47, 1, 3);
        p4.add(labpath, 2, 3);
        p4.add(Status, 0, 11);
        p4.add(img, 1, 10);
        p4.add(hb4, 1, 5);
        p4.setStyle("-fx-background-color: #00CCCC;");
        Scene s4 = new Scene(p4, 350, 350);
        sell.setScene(s4);
        sell.setTitle("sell  ");
        sell.show();
    }

    /////
    void GetProducts(ProductClass Objs[]) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            Statement statement = con.createStatement();
            /*
 ID 
 phurl    
 FName    
 Email
 Name 
 Price
 Number
 DateOfAdded
             */
            System.out.println("Start To Insert Products To Object ");
            ResultSet ret = statement.executeQuery("select * from products where Email != '" + User.getEmail() + "';");
            while (ret.next()) {
                Objs[Id] = new ProductClass();
                System.out.println(Id);
                Objs[Id].SetDate(ret.getString("DateOfAdded"));
                System.out.println("1");
                Objs[Id].SetEmail(ret.getString("Email"));
                System.out.println("2");
                Objs[Id].SetFName(ret.getString("FName"));
                System.out.println("3");
                Objs[Id].SetId(ret.getInt("ID"));
                System.out.println("4");
                Objs[Id].SetPhurl(ret.getString("Phurl"));
                System.out.println("5");
                int Pr = ret.getInt("Price");
                System.out.println("6");
                int Nu = ret.getInt("Number");
                System.out.println("7");
                Objs[Id].SetPriceAndNumber(Pr, Nu);
                Objs[Id].SetProductName(ret.getString("Name"));
                System.out.println("8");
                Id++;
            }
        }
        System.out.println("Done Insert TO Objects ");
    }
    ImageView Img;
    ComboBox<Integer> Amount;
    ComboBox<ProductClass> Products;
    GridPane Status;
    Label TotPrice;
    Label L[];

    void ShowInformation() {

        System.out.println("Start\n");
        ProductClass P = Products.getValue();
        final String Values[] = P.GetInfo();
        Status.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            Status.add(L[i], 0, i);
            Status.add(new Label(Values[i]), 1, i);
        }
        Status.add(TotPrice, 1, 4);
        Status.add(new Label("TotalPrice: "), 0, 4);
        TotPrice.setText("" + P.getPrice());
        Amount.getItems().clear();

        for (int i = 1; i <= P.getNumber(); i++) {
            Amount.getItems().add(i);
        }
        Amount.getSelectionModel().selectFirst();
        try {
            Img.setImage(new Image(P.getPhurl()));
        } catch (IllegalArgumentException El) {
            System.out.println("url Is Wrong ! " + P.getPhurl());
        }
        Img.setFitHeight(200);
        Img.setFitWidth(300);
        System.out.println("End");
    }

    void UpdateLogTable(String Seller, String SellerName, String Buyer, String ProductName, int Price, int Number) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive IN Login");
            String curQ = "insert into sales ( SellerEmail , SellerName ,  BuyerEmail , ProductName , Price , Number ) values ( ? , ? , ? , ? , ? , ? );";
            PreparedStatement statement = con.prepareStatement(curQ);
            statement.setString(1, Seller);
            statement.setString(2, SellerName);
            statement.setString(3, Buyer);
            statement.setString(4, ProductName);
            statement.setInt(5, Price);
            statement.setInt(6, Number);
            statement.executeUpdate();
        }

    }

    public void buy() throws Exception {
        buy = new Stage();
        Label Stat = new Label();
        TotPrice = new Label();
        String Labs[] = {"The Seller", "ProductName", "Price", "AvilabeNumber"};
        L = new Label[4];
        for (int i = 0; i < 4; i++) {
            L[i] = new Label(Labs[i]);
        }

        Img = new ImageView();
        Status = new GridPane();
        BorderPane b5 = new BorderPane();
        ProductClass Objs[] = new ProductClass[100];
        Id = 0;
        GetProducts(Objs);
        Amount = new ComboBox<>();
        Products = new ComboBox<>();
        for (int i = 0; i < Id; i++) {
            Products.getItems().add(Objs[i]);
        }

        Products.setOnAction(e -> {
            ShowInformation();
        });
        Amount.setOnAction(e -> {
            try {

                System.out.println("Start Amont Ac");
                TotPrice.setText(Integer.toString(Products.getValue().getPrice() * Amount.getValue()));
            } catch (NullPointerException E) {
                System.out.println("Here ! ! ");
                System.out.println(E.getMessage());
            }
            System.out.println("End Amount Ac ");
        });
        Button b51 = new Button("buy ");
        b51.setOnAction(e -> {
            ProductClass P = Products.getValue();
            int Number = Amount.getValue();

            int Price = P.getPrice() * Number;
            if (Price > User.getMoney()) {
                Stat.setText("FaildToBuy You Need  " + (Price - User.getMoney()) + " To able To Buy ");
            } else {
                System.out.println("But Btn Strart");
                int Tm = User.getMoney();
                try {
                    User.SetMoney(Integer.toString(User.getMoney() - Price));
                    User.Update();
                    System.out.println("User Done OK ");
                    P.SetPriceAndNumber(P.getPrice(), P.getNumber() - Number);
                    // Update Log Table
                    UpdateLogTable(P.getEmail(), P.getName(), User.getEmail(), P.getProductName(), P.getPrice(), Amount.getValue());
                    if (P.Update()) {
                        Products.getItems().remove(P);
                    }
                    ShowInformation();
                    System.out.println("Product Done ok");
                    UserClass TemObj = new UserClass();
                    TemObj.SETEMAIL(P.getEmail());
                    TemObj.UpdateMoney(Price);
                    TemObj = null;
                    Stat.setText("Done!");

                } catch (Exception E) {
                    System.out.println(E.getMessage());
                    System.out.println("Error Ouccur in Buying");
                    User.SetMoney(Integer.toString(Tm));
                }
            }
        });
        Button b52 = new Button("back ");
        b52.setOnAction(e -> {
            buy.close();
            want.show();
        });
        Status.setStyle("-fx-font: normal bold 20px 'serif' ");
        b51.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        b52.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        FlowPane Info = new FlowPane();
        Info.setHgap(100);
        Info.getChildren().addAll(Status, Img);
        Info.setAlignment(Pos.CENTER);
        b5.setCenter(Info);
        b5.setStyle("-fx-background-color: #00CCCC;");

        FlowPane TopPane = new FlowPane();
        TopPane.getChildren().addAll(Products, Amount, b51, b52, Stat);
        TopPane.setHgap(30);
        b5.setTop(TopPane);

        Scene s5 = new Scene(b5, 600, 400);
        buy.setScene(s5);
        buy.setResizable(false);

        buy.setTitle("buy  ");
        buy.show();

    }
/////
    GridPane g8;

    void CreateSalesTable2() throws Exception {
        System.out.println("Start C2 ");
        String Tap[] = {"Buyer", "Product", "Price", "Number", "DateOfSale"};
        for (int i = 0; i < 5; i++) {
            Label L = new Label("|" + Tap[i]);
            L.setStyle("-fx-text-fill:red;");
            g8.add(L, i, 0);
        }
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive");
            Statement statement = con.createStatement();
            String curQ = "select BuyerEmail , ProductName , Price , Number , DateOfSale  from sales where SellerEmail = '" + User.getEmail() + "';";
            System.out.println(curQ);
            ResultSet ret = statement.executeQuery(curQ);
            int Row = 1;
            while (ret.next()) {
                for (int i = 0; i < 5; i++) {
                    if (i != 2) {
                        g8.add(new Label("|" + ret.getString(i + 1)), i, Row);
                    } else {
                        g8.add(new Label("|" + ret.getInt(i + 1)), i, Row);
                    }
                }
                Row++;
            }
        }
    }

    public void getsales() throws Exception {

        sales = new Stage();
        g8 = new GridPane();
        CreateSalesTable2();
        Button b882 = new Button("back ");
        b882.setOnAction(e -> {
            sales.close();
            want.show();
        });
        b882.setStyle(
                "-fx-background-color: green; -fx-text-fill: white;");
        VBox box = new VBox();
        ScrollPane SP = new ScrollPane();
        box.getChildren().addAll(b882, g8);

        g8.setStyle("-fx-background-color: #00CCCC;");
        g8.setStyle("-fx-font: normal bold 30px 'serif' ");
        SP.setContent(box);
        Scene s7 = new Scene(SP, 900, 500);
        sales.setScene(s7);
        sales.setTitle("the sales");
        sales.setResizable(false);

        sales.show();
    }
/////////
    GridPane g9;

    void CreateSalesTable() throws Exception {
        String Tap[] = {"Seller", "Product", "Price", "Number", "DateOfSale"};
        for (int i = 0; i < 5; i++) {
            Label L = new Label("|" + Tap[i]);
            L.setStyle("-fx-text-fill:red;");
            g9.add(L, i, 0);
        }
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaprojectdb", "root", "")) {
            System.out.println("Connection is Alive");
            Statement statement = con.createStatement();
            String curQ = "select SellerName , ProductName , Price , Number , DateOfSale  from sales where BuyerEmail = '" + User.getEmail() + "';";
            System.out.println(curQ);
            ResultSet ret = statement.executeQuery(curQ);
            int Row = 1;
            while (ret.next()) {
                for (int i = 0; i < 5; i++) {
                    if (i != 2) {
                        g9.add(new Label("|" + ret.getString(i + 1)), i, Row);
                    } else {
                        g9.add(new Label("|" + ret.getInt(i + 1)), i, Row);
                    }
                }
                Row++;
            }
        }
    }

    public void getPurchases() throws Exception {
        Purchases = new Stage();
        g9 = new GridPane();
        CreateSalesTable();
        ScrollPane SP = new ScrollPane();

        Button b91 = new Button("back ");
        b91.setOnAction(e -> {
            Purchases.close();
            want.show();
        });
        b91.setStyle(
                "-fx-background-color: green ; -fx-text-fill: white;");
        g9.setStyle("-fx-background-color: green ; -fx-text-fill: white;");

        g9.setStyle("-fx-font: normal bold 30px 'serif' ");

        VBox box = new VBox();
        box.getChildren().addAll(b91, g9);

        SP.setContent(box);
        Scene s8 = new Scene(SP, 900, 500);
        Purchases.setScene(s8);
        Purchases.setTitle("the Purchases");
        Purchases.setResizable(false);
        Purchases.show();
    }

    /////////
    public void getabout() {
        about = new Stage();
        GridPane g6 = new GridPane();
        Label Statue = new Label();
        Label l61 = new Label("First Name ");
        Label l62 = new Label("last Name ");
        Label l64 = new Label("Email : ");
        Label l65 = new Label("Password: ");
        Label l66 = new Label("Money: ");
        Label l67 = new Label("Age : ");
        Label t67 = new Label(User.getAge());
        TextField tf61 = new TextField(User.getFName());
        TextField tf62 = new TextField(User.getLName());
        Label tf64 = new Label(User.getEmail());
        Label t66 = new Label("" + User.getMoney());
        TextField tf65 = new TextField(User.getPassword());
        Button b61 = new Button("back");
        Button b62 = new Button("update");

        b61.setOnAction(e -> {
            about.close();
            want.show();
        });
        b62.setOnAction(e -> {
            try {
                boolean done = true;
                String T1 = User.getFName();
                String T2 = User.getLName();
                if (!User.SetFLName(tf61.getText(), tf62.getText())) {
                    done = false;
                }
                User.SetFLName(T1, T2);
                T1 = User.getPassword();
                if (!User.SetPassword(tf65.getText())) {
                    done = false;
                }
                if (done == true) {
                    User.SetFLName(tf61.getText(), tf62.getText());
                    User.SetPassword(tf65.getText());
                    try {
                        User.Update();
                        Statue.setText("Done!");
                        tf61.setStyle("-fx-border-color:blue;");
                        tf62.setStyle("-fx-border-color:blue;");
                        tf65.setStyle("-fx-border-color:blue;");
                    } catch (Exception EU) {
                        tf61.setStyle("-fx-border-color:red;");
                        tf62.setStyle("-fx-border-color:red;");
                        tf65.setStyle("-fx-border-color:red;");
                        Statue.setText(EU.getMessage());
                        System.out.println("Can't Update ");
                        g6.setStyle("-fx-background-color: red;");
                    }
                } else {
                    tf61.setStyle("-fx-border-color:red;");
                    tf62.setStyle("-fx-border-color:red;");
                    tf65.setStyle("-fx-border-color:red;");
                }
            } catch (Exception E) {
                tf61.setStyle("-fx-border-color:red;");
                tf62.setStyle("-fx-border-color:red;");
                tf65.setStyle("-fx-border-color:red;");
                Statue.setText(E.getMessage());
            }
        });
        HBox hb6 = new HBox();
        hb6.setSpacing(20);
        hb6.setAlignment(Pos.CENTER);
        hb6.getChildren().addAll(b61, b62);
        g6.setVgap(5);
        g6.setHgap(5);
        g6.setAlignment(Pos.CENTER);

        g6.add(l64, 0, 0);
        g6.add(tf64, 1, 0);
        g6.add(l66, 0, 1);
        g6.add(t66, 1, 1);
        g6.add(l67, 0, 2);
        g6.add(t67, 1, 2);
        g6.add(l61, 0, 3);
        g6.add(tf61, 1, 3);

        g6.add(l62, 0, 4);
        g6.add(tf62, 1, 4);

        g6.add(l65, 0, 5);
        g6.add(tf65, 1, 5);
        g6.add(Statue, 0, 6);
        g6.add(hb6, 1, 7);

        b61.setStyle(
                "-fx-background-color: green; -fx-text-fill: white;");
        b62.setStyle(
                "-fx-background-color: green; -fx-text-fill: white;");
        g6.setStyle("-fx-background-color: #00CCCC;");
        Scene s6 = new Scene(g6, 400, 400);
        about.setScene(s6);
        about.setTitle("About");
        about.setResizable(false);

        about.show();
    }

    @Override
    public void start(Stage LOL) throws ClassNotFoundException, SQLException, Exception {
        User = new UserClass();
        primaryStage = new Stage();
        BorderPane p0 = new BorderPane();
        Button b01 = new Button("Register");
        Button b02 = new Button("login");

        b01.setOnAction(e -> {
            try {
                primaryStage.close();
                regest();
            } catch (Exception ex1) {
                System.out.println(ex1.getMessage());
            }
        });
        b02.setOnAction(e -> {
            try {
                primaryStage.close();
                log();
            } catch (Exception ex1) {
                System.out.println(ex1.getMessage());
            }
        });

        b01.setStyle(
                "-fx-background-color: #00CCCC; -fx-text-fill: white;");
        b02.setStyle(
                "-fx-background-color: #00CCCC; -fx-text-fill: white;");
        HBox hb0 = new HBox();
        hb0.setSpacing(20);
        hb0.setAlignment(Pos.CENTER);
        hb0.setStyle("-fx-background-color:#004C99 ");
        hb0.getChildren().addAll(b01, b02);

        ImageView img = new ImageView();
        try {
            img = new ImageView("mm.jpg");
        } catch (Exception e) {

        }

        img.setFitHeight(200);
        img.setFitWidth(350);
        Text app = new Text(150, 20, "Market " + "  ");
        Font f = Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 20);
        app.setFont(f);
        Pane pst = new Pane();
        pst.getChildren().add(app);
        p0.setTop(pst);
        p0.setCenter(img);
        p0.setBottom(hb0);
        p0.setStyle("-fx-background-color: #004C99;");
        Scene s0 = new Scene(p0, 350, 350);
        primaryStage.setResizable(false);
        primaryStage.setTitle("my APP");
        primaryStage.setScene(s0);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
