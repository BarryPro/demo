package com.barry.demo.tools;

import com.barry.demo.util.FileUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

/**
 * 压测数据孵化器
 *
 * @author yuchenglong03
 * @since 2019-03-22 19:50
 */
public class PressureDataIncubator {
    /**
     * 生成压测数据
     *
     * @return 返回需要压测的内容
     */
    private String genPressureData(int size, List<Integer> cityIdList) {
        StringBuilder stringBuilder = new StringBuilder();
        // 1.文件头
        String title = "ax_biz_scenario_type,axb_biz_scenario_type,biz_type,city_id,duration,outer_unique_id,phone_a,phone_b,user_id";
        stringBuilder.append(title).append("\n");
        // 2.biz_scenario_type数组
        String [] bizScenarioTypeArry = {"WAIMAI_CUSTOMER_CALL_MERCHANT","WAIMAI_CUSTOMER_CALL_RIDER","WAIMAI_RIDER_CALL_CUSTOMER"};
        // 3.基础唯一id
        long baseOuterUniqueId = 10000000L;
        long basePhoneA = 12345678910L;
        long basePhoneB = 22345678911L;
        long baseUserId = 100000L;
        // 4.文件内容
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // 初始化数据参数
            RequestData requestData = new RequestData();
            requestData.setBiz_scenario_type(bizScenarioTypeArry[random.nextInt(3)]);
            requestData.setDuration(random.nextInt(10) + 1);
            requestData.setBiz_type("WAIMAI");
            // 根据UniqueId来灰度区分设置cityID
            // 每次加1
            baseOuterUniqueId += 1;
            // 每次加3
            baseUserId += 3;
            basePhoneA += random.nextInt(4) + 1;
            basePhoneB += random.nextInt(4) + 1;
            requestData.setOuter_unique_id(baseOuterUniqueId);

            requestData.setCity_id(genPressureSupplierCityId(requestData.getOuter_unique_id(),cityIdList));
            requestData.setPhone_a(basePhoneA);
            requestData.setPhone_b(basePhoneB);
            requestData.setUser_id(baseUserId);
            // 拼接查数
            String COMMA = ",";
            stringBuilder.append("WAIMAI_USER_CALLED").append(COMMA)
                    .append(requestData.getBiz_scenario_type()).append(COMMA)
                    .append(requestData.getBiz_type()).append(COMMA)
                    .append(requestData.getCity_id()).append(COMMA)
                    .append(requestData.getDuration()).append(COMMA)
                    .append(requestData.getOuter_unique_id()).append(COMMA)
                    .append(requestData.getPhone_a()).append(COMMA)
                    .append(requestData.getPhone_b()).append(COMMA)
                    .append(requestData.getUser_id())
                    .append("\n");
        }
        // 返回拼接好的文件内容
        return stringBuilder.toString();
    }

    private Integer genPressureSupplierCityId(Long outer_unique_id, List<Integer> cityIdList) {
        long scale = outer_unique_id % 100;
        if (scale < 10) {
            // 移动
            return cityIdList.get(0);
        } else if (scale < (10 + 20)) {
            // 东信
            return cityIdList.get(1);
        } else if (scale < (10 + 20 + 30)) {
            // 中联
            return cityIdList.get(2);
        } else {
            // 腾讯
            return cityIdList.get(3);
        }
    }

    public static void main(String[] args) {
        // 移动、东信、中联、腾讯
        List<Integer> cityList = Lists.newArrayList(1101,2201,3304,55);
        FileUtil.writeFile("/Users/yuchenglong03/tmp/supplierPressureData.csv",
                new PressureDataIncubator().genPressureData(1000,cityList));
        FileUtil.readFile("/Users/yuchenglong03/tmp/supplierPressureData.csv");
    }

    @Setter
    @Getter
    static class RequestData{
        private String biz_type;
        private Integer city_id;
        private Integer duration;
        private Long outer_unique_id;
        private Long phone_a;
        private Long phone_b;
        private Long user_id;
        private String biz_scenario_type;
    }
}
