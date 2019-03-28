package com.barry.demo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuchenglong03
 * @since 2019-03-28 09:47
 */

@Setter
@Getter
public class PressureRequestData {
    private String biz_type;
    private Integer city_id;
    private Integer duration;
    private Long outer_unique_id;
    private Long phone_a;
    private Long phone_b;
    private Long user_id;
    private String biz_scenario_type;
}
