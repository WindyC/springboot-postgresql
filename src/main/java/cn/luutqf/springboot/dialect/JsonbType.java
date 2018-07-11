package cn.luutqf.springboot.dialect;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.Properties;

/**
 * @Author ZhenYang
 * @Date Created in 2018/3/14 13:46
 * @Description
 */
public class JsonbType implements UserType, ParameterizedType {

    private final ObjectMapper mapper = new ObjectMapper();
    ;

    private static final ClassLoaderService classLoaderService = new ClassLoaderServiceImpl();

    public static final String CLASS = "CLASS";

    private Class<?> jsonClassType;

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {

        PGobject oo = (PGobject) resultSet.getObject(strings[0]);
        if (oo.getValue() != null) {
            System.out.println(jsonClassType.getClass());
            return JSON.parseObject(oo.getValue(), jsonClassType);
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            try {
                preparedStatement.setObject(i, mapper.writeValueAsString(o), Types.OTHER);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
//    @Override
//    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
//            throws HibernateException, SQLException {
//        if (value == null) {
//            st.setNull(index, Types.OTHER);
//        } else {
//            try {
//                st.setObject(index, mapper.writeValueAsString(value), Types.OTHER);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    /**
//     * 重写自定义类型，Jsonb
//     * @param rs
//     * @param names
//     * @param session
//     * @param owner
//     * @return
//     * @throws HibernateException
//     * @throws SQLException
//     */
//    @Override
//    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
//            throws HibernateException, SQLException {
//        PGobject o = (PGobject) rs.getObject(names[0]);
//        if (o.getValue() != null) {
//            System.out.println(jsonClassType.getClass());
//            return JSON.parseObject(o.getValue(),jsonClassType);
//        }
//        return null;
//    }

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue != null) {
            try {
                System.out.println(mapper.readValue(mapper.writeValueAsString(originalValue), returnedClass()));
                return mapper.readValue(mapper.writeValueAsString(originalValue), returnedClass());
            } catch (IOException e) {
                throw new HibernateException("Failed to deep copy object", e);
            }
        }
        return null;
    }


    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);

        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }

        throw new SerializationException(
                String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null) {
            return 0;
        }

        return x.hashCode();
    }


    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final String clazz = (String) parameters.get(CLASS);
        jsonClassType = classLoaderService.classForName(clazz);

    }

}