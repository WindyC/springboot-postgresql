package cn.luutqf.springboot.repository;

import cn.luutqf.springboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author : ZhenYang
 * @Despriction :
 * @Date: Created in 2018/7/11 10:11
 * @Modify By:
 */
public interface PersonRepository extends JpaRepository<Person,Long> {
    @Query(value = "select * from luutqf.person b where b.tags ->>?1 = ?2 ", nativeQuery = true)
    List<Person> selectMap(String tag, String value);
}
