package cn.luutqf.springboot.web;

import cn.luutqf.springboot.entity.Body;
import cn.luutqf.springboot.entity.Name;
import cn.luutqf.springboot.entity.Person;
import cn.luutqf.springboot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author : ZhenYang
 * @Despriction :
 * @Date: Created in 2018/7/11 10:16
 * @Modify By:
 */
@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping
    public Object test01(@RequestParam(defaultValue = "12344") String key,@RequestParam(defaultValue = "asdfasd")String value){
        return repository.selectMap(key,value);
    }
    @PostMapping
    public Object test02(){
        Person person = new Person();
        Body body = new Body();
        body.setWeight(62.0);
        body.setStature(175.0);
        body.setCranialCapacity(999999.9);
        person.setBody(body);
        Name name = new Name();
        name.setMing("zhenyang");
        name.setXing("lu");
        name.setTitle("灵宝天尊");
        name.setZi("孔明");
        person.setName(name);
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("12344","asdfasd");
        map.put("4545","gfdgdfg");
        person.setTags(map);
        List<String> wifes = new ArrayList<>();
        wifes.add("乐正绫");
        wifes.add("双笙子");
        wifes.add("柳梦璃");
        person.setWifes(wifes);

        return repository.save(person);
    }
    @PutMapping
    public Object test03(){
        return null;
    }
    @DeleteMapping
    public Object test04(){
        return null;
    }
}
