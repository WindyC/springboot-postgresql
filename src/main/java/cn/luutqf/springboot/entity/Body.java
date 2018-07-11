package cn.luutqf.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : ZhenYang
 * @Despriction :
 * @Date: Created in 2018/7/11 9:53
 * @Modify By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Body {
    private Double stature;
    private Double weight;
    private Double cranialCapacity;
}
