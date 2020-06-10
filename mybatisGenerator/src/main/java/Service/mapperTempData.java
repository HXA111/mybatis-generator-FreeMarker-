package Service;

import util.dbMeteData;
import vo.fieldList;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class mapperTempData {
    private static String mapperPackageName;
    private static String saveRootPath;

    static {
        Properties pro = new Properties();
        InputStream in = voTempData.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            pro.load(new InputStreamReader(in, "utf-8"));
            in.close();
            mapperPackageName = pro.getProperty("mapperPackageName");
            saveRootPath=pro.getProperty("saveRootPath");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //mapper模板对应的数据模型的集合，包含每张表的数据模型
    public static List<Map<String,Object>> getMapperData(){
        List<Map<String,Object>> mapperList=new ArrayList<>();
        List<String> tableNames=new ArrayList<>();//获取表名
        tableNames= dbMeteData.getTableName();
        List<String> voNames=voTempData.getVoName();//获取vo名
        for (int i = 0; i <tableNames.size() ; i++){
            List<fieldList> fieldList=new ArrayList<>();
            fieldList keyField=new fieldList();
            String voName=voNames.get(i);
            String tableName=tableNames.get(i);
            String voPackageName=voTempData.getVoPackageName();//
            String daoPackageName=daoTempData.getDaoPackageName();
            keyField=dbMeteData.getKeyField(tableName);
            List<String> columnsName=dbMeteData.getColumnsName(tableName);
            List<String> columnsType=dbMeteData.getColumnsType(tableName);
            for (int j = 0; j <columnsName.size() ; j++) {
                fieldList field=new fieldList();
                String name=columnsName.get(j);
                field.setName(name);
                String fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
                field.setFieldName(fieldName);
                String columnType=columnsType.get(j);
                String type = dbMeteData.javaType(columnType);
                field.setType(type);
                fieldList.add(field);
            }
            Map<String,Object> mapperData=new HashMap<>();
            mapperData.put("daoPackageName",daoPackageName);
            mapperData.put("voName",voName);
            mapperData.put("voPackageName",voPackageName);
            mapperData.put("keyField",keyField);
            mapperData.put("fieldList",fieldList);
            mapperList.add(mapperData);
        }
        return mapperList;
    }

    //获取mapper映射文件生成的路径
    public static String getMapperPath(){
        String mapperPath=saveRootPath;
        String []strings=mapperPackageName.split("\\.");//获取mapper类的路径和包名
        int length=strings.length;
        for (int i = 0; i <length; i++) {
            mapperPath=mapperPath+"/"+strings[i];
        }
        File file=new File(mapperPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return mapperPath;
    }
}
