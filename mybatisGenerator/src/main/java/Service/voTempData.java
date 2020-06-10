package Service;

import util.dbMeteData;
import vo.fieldList;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class voTempData {
    private static String voPackageName;
    private static String saveRootPath;

    static {
        Properties pro = new Properties();
        InputStream in = voTempData.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            pro.load(new InputStreamReader(in, "utf-8"));
            in.close();
            voPackageName = pro.getProperty("voPackageName");
            saveRootPath=pro.getProperty("saveRootPath");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //vo模板对应的数据模型的集合，包含每张表的数据模型
    public static List<Map<String,Object>> getVoData(){
        List<Map<String,Object>> voList=new ArrayList<>();
        String []strings=voPackageName.split("\\.");//获取vo 类的路径和包名
        int length=strings.length;
        String packageName=strings[length-1];//获取vo的包名
        List<String> tableNames=new ArrayList<>();
        tableNames= dbMeteData.getTableName();//获得数据库中的所有表名
        for (String tableName:tableNames) {
            Map<String,Object> VoData=new HashMap<>();
            String className = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
            List<fieldList> fieldList=new ArrayList<>();
            List<String> columnsName=dbMeteData.getColumnsName(tableName);//获取对应表中的所有字段名
            List<String> columnsType=dbMeteData.getColumnsType(tableName);//获取对应表中的所有字段的数据库类型
            for (int i = 0; i <columnsName.size() ; i++) {
                fieldList field=new fieldList();
                String name=columnsName.get(i);
                field.setName(name);
                String fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
                field.setFieldName(fieldName);
                String columnType=columnsType.get(i);
                String type = dbMeteData.javaType(columnType);
                field.setType(type);
                fieldList.add(field);
            }
            VoData.put("packageName",packageName);
            VoData.put("className",className);
            VoData.put("fieldList",fieldList);
            voList.add(VoData);
        }
        return voList;
    }

    //返回vo文件生成的路径
    public static String getVoPath(){
        String voPath=saveRootPath;
        String []strings=voPackageName.split("\\.");//获取vo类的路径和包名
        int length=strings.length;
        for (int i = 0; i <length; i++) {
            voPath=voPath+"/"+strings[i];
        }
        File file=new File(voPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return voPath;
    }

    //返回vo文件所有表的对应vo实体类名集合
    public static List<String> getVoName(){
        List<String> voNameList=new ArrayList<>();
        List<String> tableNames=new ArrayList<>();
        tableNames= dbMeteData.getTableName();
        for (String tableName:tableNames){
            String className = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
            voNameList.add(className);
        }
        return voNameList;
    }

    //返回vo 类的包名
    public static String getVoPackageName(){
        String []strings=voPackageName.split("\\.");//获取vo 类的路径和包名
        int length=strings.length;
        String packageName=strings[length-1];
        return packageName;
    }
}
