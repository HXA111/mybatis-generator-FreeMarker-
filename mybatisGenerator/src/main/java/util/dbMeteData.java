package util;

import vo.fieldList;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class dbMeteData {
    private static Connection con;
    private static String url;
    private static String driver;
    private static String user;
    private static String password;

    static {
        Properties pro = new Properties();
        InputStream in = dbMeteData.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            pro.load(new InputStreamReader(in, "utf-8"));
            in.close();
            driver = pro.getProperty("driver");
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String > getTableName(){
        List<String> tableNames=new ArrayList<>();
        try {
            con = DriverManager.getConnection(url, user, password);
            DatabaseMetaData metaData = con.getMetaData();
            //获取数据库的表信息
            ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                //获取数据库的表名
                String tableName = rs.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tableNames;
    }

    //获取数据库表中的字段名
    public static List<String> getColumnsName(String tableName) {
        List<String> columnsName = new ArrayList<>();
        try {
            con = DriverManager.getConnection(url, user, password);
            DatabaseMetaData metaData = con.getMetaData();
            //获取表中字段信息
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            while (rs.next()) {
                //获取字段名
                String name = rs.getString("COLUMN_NAME");
                columnsName.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return columnsName;
    }

    //获取数据库表中的属性的类型
    public static List<String> getColumnsType(String tableName){
        List<String> columnsType =new ArrayList<>();
        try {
            con=DriverManager.getConnection(url, user, password);
            DatabaseMetaData metaData = con.getMetaData();
            //获取表中字段信息
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            while(rs.next()){
                String columnType = rs.getString("TYPE_NAME");
                columnsType.add(columnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return columnsType;
    }

    //获取数据库表中的主键名称
    public static fieldList getKeyField(String tableName){
        fieldList keyField=new fieldList();
        String name="";
        String fieldName="";
        String type="";
        try {
            con=DriverManager.getConnection(url, user, password);
            DatabaseMetaData metaData = con.getMetaData();
            //获取表中字段信息
            ResultSet rs = metaData.getPrimaryKeys(null,null,tableName);
            while(rs.next()){
                name=rs.getString("COLUMN_NAME");
                keyField.setName(name);
                fieldName=name.substring(0, 1).toLowerCase() + name.substring(1);
                keyField.setFieldName(fieldName);
                ResultSet rs1 = metaData.getColumns(null, null, tableName, name);
                while(rs1.next()){
                    type=rs1.getString("TYPE_NAME");
                    type=javaType(type);
                }
                keyField.setType(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return keyField;
    }

    //将数据库中字段类型转化为对应的java类型
    public static String javaType(String columnType) {
        String Type = String.valueOf(columnType);
        String type = "";
        switch (Type) {
            case "VARCHAR":
                type = "String";
                break;
            case "DECIMAL":
                type = "double";
                break;
            case "INT":
                type = "int";
                break;
            case "DATETIME":
                type = "String";
                break;
            default:
                type = "";
        }//后面可以自己添加类型
        return type;
    }
}
