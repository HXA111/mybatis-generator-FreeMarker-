package ${packageName};

public class ${className}{
<#list fieldList as field>
    private ${field.type} ${field.name};
</#list>

    public ${className}() {
        super();
    }

    public ${className}(<#list fieldList as field>${field.type} ${field.name}<#sep>,</#list>) {
    <#list fieldList as field>
        this.${field.name}=${field.name};
    </#list>
    }

<#list fieldList as field>
    public void set${field.name?cap_first}(${field.type} ${field.name}) {
        this.${field.name}=${field.name};
    }

    public ${field.type} get${field.name?cap_first}() {
        return ${field.name};
    }
</#list>

    public String toString() {
        return "${className}{"+
    <#list fieldList as field>"${field.name}="+${field.name}<#sep >+","+</#list>;
    }
}