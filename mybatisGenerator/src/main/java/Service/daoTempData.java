package Service;

import util.dbMeteData;
import vo.fieldList;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class daoTempData {
    private static String daoPackageName;
    private static String saveRootPath;

    static {
        Properties pro = new Properties();
        InputStream in = voTempData.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            pro.load(new InputStreamReader(in, "utf-8"));
            in.close();
            daoPackageName = pro.getProperty("daoPackageName");
            saveRootPath=pro.getProperty("saveRootPath");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //dao模板对应的数据模型的集合，包含每张表的数据模型
    public static List<Map<String,Object>> getDaoData(){
        List<Map<String,Object>> daoList=new ArrayList<>();
        String []strings=daoPackageName.split("\\.");//获取dao类的路径和包名
        int length=strings.length;
        String daoPackageName=strings[length-1];
        List<String> tableNames=new ArrayList<>();//获取表名
        tableNames= dbMeteData.getTableName();
        List<String> voNames=voTempData.getVoName();//获取vo名
        for (int i = 0; i <tableNames.size() ; i++) {
            fieldList keyField=new fieldList();
            String voName=voNames.get(i);
            String tableName=tableNames.get(i);
            String voPackageName=voTempData.getVoPackageName();
            keyField=dbMeteData.getKeyField(tableName);
            Map<String,Object> daoData=new HashMap<>();
            daoData.put("keyField",keyField);
            daoData.put("voName",voName);
            daoData.put("voPackageName",voPackageName);
            daoData.put("daoPackageName",daoPackageName);
            daoList.add(daoData);
        }
        return daoList;
    }

    //返回dao文件生成的路径
    public static String getDaoPath(){
        String daoPath=saveRootPath;
        String []strings=daoPackageName.split("\\.");//获取dao类的路径和包名
        int length=strings.length;
        for (int i = 0; i <length; i++) {
            daoPath=daoPath+"/"+strings[i];
        }
        File file=new File(daoPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return daoPath;
    }

    //返回dao 类的包名
    public static String getDaoPackageName(){
        String []strings=daoPackageName.split("\\.");//获取dao类的路径和包名
        int length=strings.length;
        String daoPackageName=strings[length-1];
        return daoPackageName;
    }
}
