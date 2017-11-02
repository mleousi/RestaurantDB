/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant1;
/**
 *
 * @author Maria
 */
import java.io.IOException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Restaurant1 {
    /**
     * @param args the command line arguments
     */    
    private final String db_name="jdbc:mysql://localhost:3306/restaurant1", username="root", password="" ;
    public PreparedStatement preparedStatement = null, cps = null, cps1=null, cps2=null,aps = null;
    public Connection connect = null;
    public Statement statement = null, cst = null, cst1 = null, cst2=null, cst3=null ,ast = null, ast1 = null;
    public ResultSet resultSet = null, ars1 = null, ars2 = null, ars3 = null, ars4=null, crs1 = null, crs2 = null, crs3 = null, crs4= null,crs = null, ars = null;
    int s,achoice,a,b,c,d;
    int cchoice,chairs,cquan,order_choice;
    int key, rid=1 ,rid2, oid;
    String adish,atable,achair,cid,cid1,price,rid1;
    String cn,cph,cdate,ctime,corder;
    Double aprice,bill;
    /*
        Trapezi: (ID_table,chairs)
        Reservation: (ID_reservation,client_name,ID_table,number_of_people,reservation_date,reservation_time)
        Menu: (dish_name,price)
        Paragelia: (ID_order,ID_reservation,client_name,dish_name,quantity,bill)       
    */
    
    public static void main(String[] args) {
        // TODO code application logic here
        Restaurant1 program = new Restaurant1();
        program.start();
    }

    public void start(){
        try {
            do{
                Scanner user_input = new Scanner(System.in);
                Scanner input1 = new Scanner(System.in);
                System.out.println();
                System.out.println("Choose 1 for Admin menu or 2 for Client menu: ");
                s = user_input.nextInt();
                connect = DriverManager.getConnection(db_name, username, password);
    //ADMIN MENU           
                if(s==1){
                    int times=0;
                    do{
                        System.out.println("Enter key: ");
                        Scanner in = new Scanner(System.in);
                        key = in.nextInt();
                        if(key==1234){
                            do{  
                                System.out.println();
                                System.out.println("Choose:");
                                System.out.println("1 For managing Menu");
                                System.out.println("2 For managing Tables");
                                System.out.println("0 For exit Admin's menu");
                                achoice = user_input.nextInt();

        //MENU                   
                                if(achoice==1){
                                    do{
                                        System.out.println();
                                        System.out.println("Managing Menu:");
                                        System.out.println("Select...");
                                        System.out.println("1.Show menu");
                                        System.out.println("2.Add a dish");
                                        System.out.println("3.Remove a dish");
                                        System.out.println("4.Clear menu");
                                        System.out.println("0.Exit");
                                        a = input1.nextInt();
                                        switch(a){
                                            case 1:
                                                System.out.println();
                                                System.out.println("Show menu: ");
                                                System.out.println("Dish Name"+"\t"+"Price");
                                                statement = connect.createStatement();
                                                resultSet = statement.executeQuery("SELECT * FROM Menu");
                                                while(resultSet.next()){
                                                    System.out.println(resultSet.getString("dish_name") +"\t"+"\t"+ resultSet.getDouble("price")); //emfanizei to menu
                                                }
                                                break;
                                            case 2:
                                                System.out.println();
                                                System.out.println("Add a dish: ");
                                                Scanner dish2 = new Scanner(System.in);
                                                adish = dish2.nextLine();
                                                System.out.println("Add price: ");
                                                price = dish2.nextLine();
                                                aprice = Double.parseDouble(price);
                                                preparedStatement = connect.prepareStatement("INSERT INTO Menu (dish_name,price) VALUES(?,?)");
                                                preparedStatement.setString(1, adish);
                                                preparedStatement.setDouble(2, aprice);
                                                preparedStatement.executeUpdate();
                                                System.out.println("Menu ready!"); 
                                                break;
                                            case 3:
                                                System.out.println();
                                                System.out.println("Remove a dish: ");
                                                Scanner dish3 = new Scanner(System.in);
                                                adish = dish3.nextLine();
                                                preparedStatement = connect.prepareStatement("DELETE FROM Menu WHERE dish_name = '"+adish+"' ");
                                                preparedStatement.executeUpdate();
                                                System.out.println("Menu ready!");
                                                break;
                                            case 4:
                                                System.out.println();
                                                System.out.println("Clear menu: ");
                                                preparedStatement = connect.prepareStatement("DELETE FROM Menu WHERE 1=1");
                                                preparedStatement.executeUpdate();
                                                System.out.println("Menu ready!");
                                                break;
                                            case 0: //na me paei sto prohgoumeno menu
                                                break;
                                            default:
                                                System.out.println("Error");
                                                break;
                                        }   
                                    }while(a!=0);
                                }
        //TABLES                   
                                else if(achoice==2){
                                    System.out.println();
                                    System.out.println("Managing Tables:");
                                    String[] num_of_chairs={"6","4","3","2"};
                                    do{
                                            System.out.println();
                                            System.out.println("Select...");
                                            System.out.println("1.Show tables");
                                            System.out.println("2.Add a table");
                                            System.out.println("3.Remove a table");
                                            System.out.println("4.Clear tables");
                                            System.out.println("0.Exit");
                                            b = input1.nextInt();
                                            switch(b){
                                                case 1:
                                                    System.out.println();
                                                    System.out.println("Show tables: ");
                                                    System.out.println("ID"+"\t"+"chairs");
                                                    statement = connect.createStatement();
                                                    resultSet = statement.executeQuery("SELECT * FROM Trapezi");
                                                    while(resultSet.next()){
                                                        System.out.println(resultSet.getString("ID_table") +"\t"+ resultSet.getString("chairs")); //emfanizei to tables
                                                    }
                                                    break;
                                                case 2:
                                                    System.out.println();
                                                    System.out.println("Add a table: ");
                                                    Scanner table = new Scanner(System.in);
                                                    System.out.println("Number of chairs (2,3,4 or 6) : ");
                                                    achair = table.nextLine();
                                                    statement = connect.createStatement();
                                                    resultSet = statement.executeQuery("SELECT MAX(ID_table) AS max_value FROM Trapezi");
                                                    resultSet.next(); //ksekinaei sto result set
                                                    int id=resultSet.getInt("max_value");
                                                    id=id+1;
                                                    for(int i=0;i<4;i++){
                                                        if ( achair.equals(num_of_chairs[i]) ) {
                                                            preparedStatement = connect.prepareStatement("INSERT INTO Trapezi (ID_table,chairs) VALUES(?,?)");
                                                            preparedStatement.setInt(1, id);
                                                            preparedStatement.setString(2, achair);
                                                            preparedStatement.executeUpdate();
                                                            System.out.println("Tables ready!");
                                                        }
                                                    }
                                                    break;
                                                case 3:
                                                    System.out.println();
                                                    System.out.println("Remove a table - Table ID: ");
                                                    Scanner table1 = new Scanner(System.in);
                                                    atable = table1.nextLine();
                                                    preparedStatement = connect.prepareStatement("DELETE FROM Trapezi WHERE ID_table = '"+atable+"' ");
                                                    preparedStatement.executeUpdate();
                                                    System.out.println("Tables ready!");
                                                    break;
                                                case 4:
                                                    System.out.println();
                                                    System.out.println("Clear tables: ");
                                                    preparedStatement = connect.prepareStatement("DELETE FROM Trapezi WHERE 1=1");
                                                    preparedStatement.executeUpdate();
                                                    System.out.println("Tables ready!");
                                                    break;
                                                case 0: //na me paei sto prohgoumeno menu
                                                    break;
                                                default:
                                                    System.out.println("Error");
                                                    break;
                                            }
                                    }while(b!=0);
                                }
                            }while(achoice!=0);
                        }
                        else{
                           System.out.println("Wrong key. Access denied.");
                           System.out.println();
                           times++;
                           //na me paei sto prohgoumeno menou ksana
                        }                
                    }while(key!=1234 && times < 3);
                }
    //CLIENT MENU
                else if(s==2){
                    
                    do{
                        System.out.println();
                        System.out.println("Welcome!");
                        System.out.println("Choose:");
                        System.out.println("1 For Reservation");
                        System.out.println("2 For Order");
                        System.out.println("0 For exit Client's menu");
                        cchoice = user_input.nextInt();
        //RESERVATION
                        if(cchoice == 1){
                            do{
                                System.out.println();
                                System.out.println("Select...");
                                System.out.println("1.Reserve a table");
                                System.out.println("2.Delete a reservation");
                                System.out.println("0.Exit");
                                c = input1.nextInt();
                                switch(c){
                                    case 1:
                                        System.out.println();
                                        System.out.println("Reserve a table:");
                                        System.out.println("Reservation date(DD/MM/YY):");
                                        Scanner date1 = new Scanner(System.in);
                                        cdate = date1.nextLine();                                       
                                        System.out.println("Reservation time(HOUR:MIN):");
                                        Scanner date2 = new Scanner(System.in);
                                        ctime = date2.nextLine();
                                        if(ctime.length() != 5){
                                            System.out.println("Invalid reservation time.");
                                            break;
                                        }
                                        String[] parts2 = ctime.split(":");
                                        String hour = parts2[0];
                                        int hour1 = Integer.parseInt(hour);
                                        String min = parts2[1];
                                        String l=hour1+":"+min;
                                        System.out.println("Number of people:");
                                        Scanner chairs1 = new Scanner(System.in);
                                        chairs = chairs1.nextInt();
                                        System.out.println("Enter your name: ");
                                        Scanner ID = new Scanner(System.in);
                                        cid = ID.nextLine();

                                        cst = connect.createStatement();
                                        //crs1 = cst.executeQuery("SELECT * FROM Trapezi WHERE ID_table ΝΟΤ IN(SELECT ID_table FROM Reservation WHERE reservation_date='"+cdate+"' AND reservation_time < '"+(hour1+3)+"'  AND reservation_time > '"+(hour1-3)+"')"); //mesa:id pou einai krathmena, eksw:id pou den anhkoun sta krathmena
                                        crs1 = cst.executeQuery("Select * From Trapezi Where ID_table NOT IN(Select ID_table From Reservation Where reservation_date ='"+cdate+"' AND reservation_time < '"+(hour1+3)+"' AND reservation_time > '"+(hour1-3)+"')");//mesa:id pou einai krathmena, eksw:id pou den anhkoun sta krathmena                     
                                        cst = connect.createStatement();
                                        crs2 = cst.executeQuery("Select COUNT(*) AS num From Trapezi Where ID_table NOT IN(Select ID_table From Reservation Where reservation_date ='"+cdate+"' AND reservation_time < '"+(hour1+3)+"' AND reservation_time > '"+(hour1-3)+"')");
                                        crs2.next();
                                        int num = crs2.getInt("num"); //posa exw kena
                                        int[][] tables = new int[num][2];
                                        int i=0;
                                        int[] Q = new int[4]; //posothtes trapeziwn
                                        int[] T = {6,4,3,2}; //eidh trapeziwn
                                        while(crs1.next()){
                                            tables[i][0] = Integer.parseInt(crs1.getString("ID_table"));
                                            tables[i][1] = Integer.parseInt(crs1.getString("chairs"));
                                            if(tables[i][1] == 6) Q[0]=Q[0]+1;
                                            else if(tables[i][1] == 4) Q[1]=Q[1]+1;
                                            else if(tables[i][1] == 3) Q[2]=Q[2]+1;
                                            else if(tables[i][1] == 2) Q[3]=Q[3]+1;
                                            i=i+1;
                                        }
                                        System.out.println(Q[0]+" "+Q[1]+" "+Q[2]+" "+Q[3]);
                                        int j,sum;
                                        int previous = T[0];
                                        int difference = T[0];
                                        boolean not_enough,done = false;
                                        String s;
                                        s = "";
                                        sum = 0; //poses theseis exw kalupsei
                                        i = 0;
                                        if (chairs < T[0]){
                                            do{
                                                if ((T[i] - chairs < difference) && (Q[i] > 0) && (T[i] >= chairs)){ //psaxnw analoga me tis diathesimes posothtes twn eidwn na vrw pou xwrane kalutera ta atoma wste na exw th mikroterh diafora
                                                    difference = T[i] - chairs;
                                                    done = true; //evala ta atoma
                                                    sum = T[i];
                                                    s = ""+sum;
                                                }
                                                i++;
                                            }while(i < 4);
                                        }

                                        difference = chairs;   
                                        i = 0;
                                        
                                        if (!done){ //gia tous sunduasmous trapeziwn (done==false)
                                             // Kanw to idio pragma gia oles tis xwrhtikothtes twn trapeziwn 6,4,3,2 mexri na kalupsw ta atoma
                                            do{
                                                j = 0;
                                                // vriskw posa trapezia tha valw apo thn sugkekrimenh xwrhtikothta
                                                do{
                                                    j++;
                                                }while(sum + j * T[i] <= chairs);
                                                
                                                not_enough = false;
                                                // An dn uparxoun tosa diathesima trapezia apo thn sugkekrimenh xwrhtikothta, vazw osa exw
                                                if (Q[i] < (j-1)){ //an den exw tosa trapezia osa thelw na valw
                                                    j = Q[i] + 1;
                                                    not_enough = true; // An dn uparxoun tosa diathesima trapezia apo thn sugkekrimenh xwrhtikothta, vazw osa exw
                                                }
                                                sum = sum + (j-1)*T[i];
                                                // vazw sto String to trapezi toses fores oses kai to pollaplasio pou vrika prin
                                                for (int k=0;k<j-1;k++)
                                                    s = s + T[i] + " ";
                                                if ((!not_enough)&&(Q[i] >= j)){ //exw diathesima & exw trapezi epithumhths xwrhtikothtas
                                                    if (((sum + T[i]) - chairs < difference) && ((sum + T[i]) - chairs >= 0)){ //vazei tin kaluterh lush
                                                        difference = (sum + T[i]) - chairs;
                                                        previous = T[i];
                                                    }
                                                }
                                                i++;
                                            }while((i < 4) && (sum < chairs));
                                        }
                                        if ((difference < chairs) && (sum != chairs)){
                                            s = s + previous;
                                            sum = sum + previous;
                                        }
                                        
                                        // an exw tsekarei ola ta trapezia kai dn exoun kalufthei ta atoma tote dn uparxei sundiasmos
                                        if (sum < chairs) System.out.println("No combination available.Reservation incomplete");
                                        else{
                                            System.out.println("Combination:" + s);
                                            String[] s_parts = s.split(" ");
                                            int k = s_parts.length;
                                            int p=0;
                                            int id=0;
                                            boolean found;
                                            int prev = Integer.parseInt(s_parts[0]);
                                            cst2 = connect.createStatement();
                                            crs3 = cst2.executeQuery("SELECT MAX(ID_reservation) AS max_value FROM Reservation");
                                            crs3.next(); //ksekinaei sto result set
                                            rid=crs3.getInt("max_value");
                                            rid=rid+1;
                                            for(int m=0;m<k;m++){
                                               if(Integer.parseInt(s_parts[m]) != prev) p=0; //tsekarei ta trapezia
                                                    found = false; //mexri na vrei to prwto trapezi me tis epithumites karekles
                                                    do{
                                                        if(tables[p][1] == Integer.parseInt(s_parts[m])){
                                                            id=tables[p][0];
                                                            found=true;
                                                            prev=Integer.parseInt(s_parts[m]);
                                                        }p++;
                                                    }while(!found);
                                                    cps = connect.prepareStatement("INSERT INTO Reservation(ID_reservation,client_name,ID_table,number_of_people,reservation_date,reservation_time) VALUES(?,?,?,?,?,?)");
                                                    cps.setInt(1, rid);
                                                    cps.setString(2, cid);
                                                    cps.setInt(3, id);
                                                    cps.setInt(4, chairs);
                                                    cps.setString(5, cdate);
                                                    cps.setString(6, l);
                                                    cps.executeUpdate();
                                            }
                                            System.out.println("Reservation complete!");
                                            System.out.println("Your reservation id is "+rid);
                                            System.out.println("Reservation ID"+"\t"+"Name"+"\t"+"Table Reserved(ID)"+"\t"+"People"+"\t"+"Date"+"\t"+"Time");
                                            cst3 = connect.createStatement();
                                            crs4 = cst3.executeQuery("SELECT * FROM Reservation WHERE client_name='"+cid+"' ");
                                            while(crs4.next()){
                                                System.out.println(crs4.getInt("ID_reservation") +"\t"+"\t"+ crs4.getString("client_name") +"\t"+ crs4.getInt("ID_table") +"\t"+"\t"+"\t"+ crs4.getInt("number_of_people") +"\t"+ crs4.getString("reservation_date")+"\t"+ crs4.getString("reservation_time")); //emfanizei to reservation
                                            }
                                        }
                                        break;
                                    case 2:
                                        System.out.println();
                                        System.out.println("Delete a reservation:");
                                        System.out.println("Enter your name:");
                                        Scanner ID1 = new Scanner(System.in);
                                        cid1 = ID1.nextLine();
                                        cps1=connect.prepareStatement("DELETE FROM Reservation WHERE client_name = '"+cid1+"' ");
                                        cps1.executeUpdate();
                                        System.out.println("Reservation deleted!");
                                        cst1 = connect.createStatement();
                                        break;
                                    case 0: //na me paei sto prohgoumeno menu
                                        break;
                                    default:
                                        System.out.println("Error");
                                        break;
                                }
                            }while(c!=0);
                        }
        //ORDER

                        else if(cchoice==2){
                            System.out.println();
                            System.out.println("Give your name:");
                            Scanner ID1 = new Scanner(System.in);
                            rid1 = ID1.nextLine();
                            ast = connect.createStatement();
                            ars2 = ast.executeQuery("SELECT COUNT(*) AS counter FROM Reservation WHERE client_name='"+rid1+"' ");
                            ast = connect.createStatement();
                            ars4=ast.executeQuery("SELECT client_name FROM Reservation WHERE ID_reservation='"+rid1+"' ");
                            ars4.next();
                            ars2.next();
                            if(ars2.getInt("counter") != 0){
                                do{
                                    System.out.println();
                                    System.out.println("Select...");
                                    System.out.println("1.Show menu");
                                    System.out.println("2.Order a dish");
                                    System.out.println("3.Show order");
                                    System.out.println("4.Delete an order");
                                    System.out.println("5.Show bill");
                                    System.out.println("0.Exit");
                                    d = input1.nextInt();
                                    switch(d){
                                        case 1:
                                            System.out.println();
                                            System.out.println("Menu:");
                                            ast = connect.createStatement();
                                            ars = ast.executeQuery("SELECT * FROM Menu");
                                            while(ars.next()){
                                                System.out.println(ars.getString("dish_name") + " " + ars.getDouble("price")); //emfanizei to menu
                                            }
                                            break;
                                        case 2:
                                            System.out.println();
                                            System.out.println("Order a dish:");
                                            System.out.println("Enter dish name: ");
                                            Scanner dish3 = new Scanner(System.in);
                                            adish = dish3.nextLine();
                                            System.out.println("Enter quantity: ");
                                            Scanner quan = new Scanner(System.in);
                                            cquan = quan.nextInt();
                                            ast = connect.createStatement();
                                            ars = ast.executeQuery("SELECT price FROM Menu WHERE dish_name='"+adish+"' "); //tha exei mia timi kathe fora
                                            cst = connect.createStatement();
                                            crs = cst.executeQuery("SELECT COUNT(*) AS fou FROM Menu WHERE dish_name='"+adish+"' ");
                                            crs.next();
                                            ars.next();
                                            if(crs.getInt("fou")!=0){
                                                ast = connect.createStatement();
                                                ars1 = ast.executeQuery("SELECT quantity,bill FROM Paragelia WHERE client_name='"+rid1+"' AND dish_name='"+adish+"' ");
                                                cst1 = connect.createStatement();
                                                crs1 = cst1.executeQuery("SELECT COUNT(*) AS pou FROM Paragelia WHERE client_name='"+rid1+"' AND dish_name='"+adish+"' ");
                                                crs1.next();
                                                ars1.next();
                                                if(crs1.getInt("pou")==0){
                                                    cst2 = connect.createStatement();
                                                    crs3 = cst2.executeQuery("SELECT MAX(ID_order) AS max_value FROM Paragelia");
                                                    crs3.next(); //ksekinaei sto result set
                                                    oid=crs3.getInt("max_value");
                                                    oid=oid+1;
                                                    cps = connect.prepareStatement("INSERT INTO Paragelia(ID_order,ID_reservation,client_name,dish_name,quantity,bill) VALUES(?,?,?,?,?,?)");
                                                    cps.setInt(1, oid);
                                                    cps.setInt(2, rid);
                                                    cps.setString(3, ars4.getString("client_name")); //ars4.getString("client_name")
                                                    cps.setString(4, adish);
                                                    cps.setInt(5, cquan);
                                                    cps.setDouble(6, ars.getDouble("price")*cquan);
                                                    cps.executeUpdate();
                                                }
                                                else{
                                                    cps1=connect.prepareStatement("UPDATE Paragelia SET quantity=?, bill=? WHERE client_name='"+rid1+"' AND dish_name='"+adish+"'");
                                                    cps1.setInt(1, ars1.getInt("quantity")+1 );
                                                    cps1.setDouble(2, (ars1.getInt("quantity")+1) * (ars.getDouble("price")) );
                                                    cps1.executeUpdate();
                                                }  
                                            }
                                            else{
                                                System.out.println("Dish name given not found in Menu.");
                                            }
                                            break;
                                        case 3:
                                            System.out.println();
                                            System.out.println("Show order:");
                                            System.out.println("Dish Name"+"\t"+"Quantity");
                                            ast = connect.createStatement();
                                            ars = ast.executeQuery("SELECT dish_name,quantity FROM Paragelia WHERE client_name='"+rid1+"' ");
                                            while(ars.next()){
                                                System.out.println(ars.getString("dish_name") +"\t"+"\t"+ ars.getString("quantity")); //emfanizei to menu
                                            }
                                            break;
                                        case 4:
                                            System.out.println();
                                            do{
                                                System.out.println();
                                                System.out.println("Delete an order:");
                                                System.out.println("Choose:");
                                                System.out.println("1 Delete a dish");
                                                System.out.println("2 Clear order");
                                                System.out.println("0 Exit");
                                                Scanner order = new Scanner(System.in);
                                                order_choice = order.nextInt();
                                                if(order_choice==1){
                                                    System.out.println();
                                                    System.out.println("Enter dish name: ");
                                                    Scanner dish2 = new Scanner(System.in);
                                                    adish = dish2.nextLine();
                                                    ast = connect.createStatement();
                                                    ars = ast.executeQuery("SELECT quantity,bill FROM Paragelia WHERE client_name='"+rid1+"' AND dish_name='"+adish+"' ");
                                                    cst2 = connect.createStatement();
                                                    crs3 = cst2.executeQuery("SELECT COUNT(*) AS lou FROM Paragelia WHERE client_name='"+rid1+"' AND dish_name='"+adish+"'");
                                                    crs3.next();
                                                    ars.next();
                                                    if( crs3.getInt("lou")!=0 ){
                                                        if( ars.getInt("quantity")-1 < 1 ){
                                                            cps1=connect.prepareStatement("DELETE FROM Paragelia WHERE client_name='"+rid1+"' AND dish_name='"+adish+"' ");
                                                            cps1.executeUpdate();
                                                        }
                                                        else{
                                                            cps1=connect.prepareStatement("UPDATE Paragelia SET quantity=?, bill=? WHERE client_name='"+rid1+"' AND dish_name='"+adish+"'");
                                                            cps1.setInt(1, ars.getInt("quantity")-1 );
                                                            cps1.setDouble(2, (  ars.getDouble("bill")/ars.getInt("quantity"))*(ars.getInt("quantity")-1) );
                                                            cps1.executeUpdate();
                                                        }
                                                    }

                                                }
                                                else if(order_choice==2){
                                                    cps1=connect.prepareStatement("DELETE FROM Paragelia WHERE client_name='"+rid1+"' ");
                                                    cps1.executeUpdate();                                
                                                }
                                                else{
                                                    //na me paei sto prohgoumeno menu
                                                }
                                            }while(order_choice!=0);
                                            break;
                                        case 5:
                                            System.out.println();
                                            System.out.println("Show bill:");
                                            ast = connect.createStatement();
                                            ars = ast.executeQuery("SELECT quantity,bill FROM Paragelia WHERE client_name='"+rid1+"' ");
                                            bill=0.0;
                                            while(ars.next()){
                                                bill = bill + ars.getDouble("bill");
                                            }
                                            System.out.println("Your bill is "+bill+" euros");
                                            break;
                                        case 0: //na me paei sto prohgoumeno menu
                                            break;
                                        default:
                                            System.out.println("Error");
                                            break;
                                    }
                                }while(d!=0);
                            }
                            else{
                                System.out.println("Invalid reservation name given.");
                            }
                        }
                        else if(cchoice==0){
                        }
                        else{
                            System.out.println("ERROR");
                        }
                    }while(cchoice!=0);
                }
                else{
                    System.out.println("ERROR");
                }
            }while(s!=0);
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
