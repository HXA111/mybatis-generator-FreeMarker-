package ${daoPackageName};
import java.util.List;
import ${voPackageName}.${voName};

public interface I${voName}DAO{
    //根据主键查找记录
    public ${voName} getBy${keyField.name?cap_first}(${keyField.type} ${keyField.name});
    //根据条件查找
    public List<${voName}> query(${voName} ${voName?uncap_first});
    //增加
    public int insert(${voName} ${voName?uncap_first});
    //修改
    public int update(${voName} ${voName?uncap_first});
    //按主键删除
    public int delete(${keyField.type} ${keyField.name});
    //按条件删除
    public int deleteCondition(${voName} ${voName?uncap_first});
}