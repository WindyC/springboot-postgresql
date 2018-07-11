package cn.luutqf.springboot.entity;

import cn.luutqf.springboot.dialect.JsonbType;
import cn.luutqf.springboot.dialect.ListJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

/**
 * @Author : ZhenYang
 * @Despriction :
 * @Date: Created in 2018/7/11 9:51
 * @Modify By:
 */
@Entity
@Table(name = "person", schema = "luutqf")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "JsonbTypeBody", typeClass = JsonbType.class, parameters = {
                @org.hibernate.annotations.Parameter(name = JsonbType.CLASS, value = "cn.luutqf.springboot.entity.Body")}),
        @TypeDef(name = "JsonbTypeName", typeClass = JsonbType.class, parameters = {
                @org.hibernate.annotations.Parameter(name = JsonbType.CLASS, value = "cn.luutqf.springboot.entity.Name")}),
        @TypeDef(name = "JsonbTypeMap", typeClass = JsonbType.class, parameters = {
                @org.hibernate.annotations.Parameter(name = JsonbType.CLASS, value = "java.util.HashMap")}),
})
public class Person {
    @Id
    @GeneratedValue
    protected long id;

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbTypeName")
    private Name name;

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbTypeBody")
    private Body body;

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbTypeMap")
    private HashMap tags;

    @Convert(converter = ListJsonConverter.class)
    private List<String> wifes;

}













