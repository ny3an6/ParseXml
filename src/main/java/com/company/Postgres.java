package com.company;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public class Postgres {

    public void connection(ArrayList<Documents> documents){

        try{
            String url = "jdbc:postgresql://172.20.1.30:32771/cabinet";
            String username = "postgres";
            String password = "postgres";
            Class.forName("org.postgresql.Driver");

            try(Connection connection = DriverManager.getConnection(url, username, password)){
//                Statement statement = connection.createStatement();
                //language=SQL
//                String sqlTest = "select * from xmldocuments";
//                ResultSet rs = statement.executeQuery(sqlTest);
//                while(rs.next()){
//                    System.out.println("ASD "+ rs.toString());
//                }


                    for (Documents doc : documents){
                        //language=SQL
                        String sql = "insert into xmldocuments(name) values (?)";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        System.out.println(doc.getValue());
                        ps.setString(1, doc.getValue());
                        ps.executeUpdate();


                    }
                } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}





