package com.ctgu.service;

import java.sql.*;
public class JDBCTest {
    public static void  main(String[] args){
        String url = "jdbc:mysql://47.107.55.17:3306/obe_system?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password="root";
        Connection conn=null;
        try{
            conn=DriverManager.getConnection(url,user,password);
            String sql="select * from t_penguin_account;";
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("id"));
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}