package vo;

public class fieldList {
    private String name;//数据库中的字段名
    private String fieldName;//对应的实体类中的属性名
    private String type;//数据库字段类型

    public fieldList() {
        super();
    }

    public fieldList(String name, String fieldName, String type) {
        this.name = name;
        this.fieldName = fieldName;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
