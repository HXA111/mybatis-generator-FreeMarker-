package Driver;

import Service.daoTempData;
import Service.mapperTempData;
import Service.voTempData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import util.dbMeteData;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Driver {
    public static void main(String[] args) throws Exception {
        List<String> tableNames = dbMeteData.getTableName();


        //1.创建FreeMarker配置类
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        //2.指定模板加载器，将模板存入缓存中
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        //3.获取模板
        Template temp = cfg.getTemplate("voTemplate.ftl");
        Template temp1=cfg.getTemplate("daoTemplate.ftl");
        Template temp2=cfg.getTemplate("mapperTemplate.ftl");
        //4.构造数据模型---vo模板
        List<Map<String, Object>> voList = voTempData.getVoData();
        for (Map<String, Object> voData : voList) {
            String voPath = voTempData.getVoPath();
            String className= (String) voData.get("className");
            //System.out.println(className);
            //System.out.println(voList.size());
            //5.文件输出
            File file = new File(voPath + "/" + className + ".java");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            temp.process(voData, pw);
            System.out.println("成功生成vo." + className + ".java文件");
        }

        //4.构造数据模型---dao模板
        List<Map<String, Object>> daoList= daoTempData.getDaoData();
        for (Map<String, Object> daoData:daoList) {
            String daoPath=daoTempData.getDaoPath();
            String className="I"+daoData.get("voName")+"DAO";
            //5.文件输出
            File file = new File(daoPath + "/" + className + ".java");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            temp1.process(daoData, pw);
            System.out.println("成功生成dao." + className + ".java文件");
        }

        //4.构造数据模型---mapper模板
        List<Map<String, Object>> mapperList= mapperTempData.getMapperData();
        for (Map<String, Object> mapperData:mapperList) {
            String mapperPath=mapperTempData.getMapperPath();
            String className=mapperData.get("voName")+"Mapper";
            //5.文件输出
            File file = new File(mapperPath + "/" + className + ".xml");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            temp2.process(mapperData, pw);
            //System.out.println(mapperPath);
            System.out.println("成功生成mapper." + className + ".xml文件");
        }
    }
}
