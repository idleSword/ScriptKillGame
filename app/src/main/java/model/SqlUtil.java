package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlUtil {//数据库
    private static final String URL = "jdbc:mysql://119.3.107.127:3306/ScriptKillGame?useUnicode=true&characterEncoding=utf-8";
    //private static final String URL = "jdbc:postgresql://222.212.253.129:5432/postgres?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";

    Connection conn;

    private Connection openConnection() throws SQLException {
        conn = null;
        final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        //final String DRIVER_NAME = "org.postgresql.Driver";
        try {
            try {
                Class.forName(DRIVER_NAME);
                System.out.println("驱动加载成功");
            }
            catch (ClassNotFoundException classNotFoundException){
                System.out.println("驱动加载错误："+classNotFoundException.toString());
                classNotFoundException.printStackTrace();
            }
            Properties props = new Properties();
            props.setProperty("user","idlesword");
            props.setProperty("password","251216");

            conn = (Connection) DriverManager.getConnection(URL, "idlesword","251216");
            //conn = (Connection) DriverManager.getConnection(URL, props);
            System.out.println("成功加载MySQL驱动！" + URL);
        } catch (Exception e) {
            System.out.println("错误:"+e.toString());
            e.printStackTrace();
        }
        return conn;
    }

    public SqlUtil(){}

    public void closeConnection(){
        try {
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql){
        ResultSet rs=null;
        try {
            conn= openConnection();
            System.out.println("连接数据库成功");

            if(conn==null)
                System.out.println("空连接");
            Statement statement=conn.createStatement();
            rs=statement.executeQuery(sql);
            while(rs.next()){
                System.out.println("id:"+rs.getString("id")+" name:"+rs.getString("name")+" hobby"+rs.getString("hobby"));
            }
            statement.close();
            conn.close();
        }
        catch (SQLException sqlException){
            System.out.println("出错了");
            sqlException.printStackTrace();
        }
        return rs;
    }


}
