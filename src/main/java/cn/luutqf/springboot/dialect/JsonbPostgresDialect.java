package cn.luutqf.springboot.dialect;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.StringType;

import java.sql.Types;

/**
 * @Author ZhenYang
 * @Date Created in 2018/3/14 13:45
 * @Description
 */
public class JsonbPostgresDialect extends PostgreSQL95Dialect {


	public JsonbPostgresDialect() {
		super();
        registerColumnType(Types.JAVA_OBJECT, "jsonb");
        registerHibernateType( Types.ARRAY, StringType.class.getName());
	}
}