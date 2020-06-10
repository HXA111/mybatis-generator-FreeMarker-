<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoPackageName}.I${voName}DAO">
    <!--当前数据库字段名与实体类中属性名不一致时，需要显示定义字段映射 -->
    <resultMap id="${voName?uncap_first}ResultMap" type="${voPackageName}.${voName}">
        <id property="${keyField.fieldName}" column="${keyField.name}"/>
        <#list fieldList as field>
            <#if field.name !=keyField.name>
                <result property="${field.fieldName}" column="${field.name}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="${voName?uncap_first}Colums">
        <#list fieldList as field>${field.name}<#sep >,</#list>
    </sql>

    <select id="getBy${keyField.name?cap_first}" parameterType="${keyField.type}" resultMap="${voName?uncap_first}ResultMap">
        select
        <include refid="${voName?uncap_first}Colums"></include>
        from ${voName?uncap_first}
        where ${keyField.name}=<#noparse>#{</#noparse>${keyField.fieldName}}
    </select>

    <select id="query" parameterType="${voPackageName}.${voName}" resultMap="${voName?uncap_first}ResultMap">
        select
        <include refid="${voName?uncap_first}Colums"></include>
        from ${voName?uncap_first}
        <where>
            <#list fieldList as field>
                <if test="${field.name}!=null">
                    and ${field.name} = <#noparse>#{</#noparse>${field.fieldName}}
                </if>
            </#list>
        </where>
    </select>

    <insert id="insert" parameterType="${voPackageName}.${voName}">
        insert into ${voName?uncap_first}(<include refid="${voName?uncap_first}Colums"></include>)
        values(<#list fieldList as field><#noparse>#{</#noparse>${field.fieldName}}<#sep>,</#list>)
    </insert>

    <update id="update" parameterType="${voPackageName}.${voName}">
        update ${voName?uncap_first}
        <set>
        <#list fieldList as field>
            <if test="${field.name}!=null">
                ${field.name} = <#noparse>#{</#noparse>${field.fieldName}}<#sep>,</#sep>
            </if>
        </#list>
        </set>
        where ${keyField.name}=<#noparse>#{</#noparse>${keyField.fieldName}}
    </update>

    <delete id="delete" parameterType="${keyField.type}">
        delete from
        ${voName?uncap_first}
        where ${keyField.name}=<#noparse>#{</#noparse>${keyField.fieldName}}
    </delete>

    <delete id="deleteCondition" parameterType="${voPackageName}.${voName}">
        delete from
        ${voName?uncap_first}
        <where>
        <#list fieldList as field>
            <if test="${field.name}!=null">
                and ${field.name} = <#noparse>#{</#noparse>${field.fieldName}}
            </if>
        </#list>
        </where>
    </delete>
    
</mapper>

