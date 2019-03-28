package com.barry.demo.tools;

import com.barry.demo.model.PressureRequestData;
import com.barry.demo.util.FileUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 压测数据孵化器-按比例集中式
 *
 * @author yuchenglong03
 * @since 2019-03-22 19:50
 */
public class PressureDataIncubator {

    // 东信
    private static final String DX = "DX";
    // 中联
    private static final String ZL = "ZL";
    // 腾讯
    private static final String TX = "TX";

    // 城市的cityID列表
    private static Map<String,Integer> cityIdMap;
    // 供应商控制比例分布map
    private static Map<String, Integer> supplierCounterMap;

    /**
     * 生成压测数据
     *
     * @return 返回需要压测的内容
     */
    private void genPressureData(int size,boolean balanceFlag,String absolutePath) {
        // 1.文件头
        String title = "ax_biz_scenario_type,axb_biz_scenario_type,biz_type,city_id,duration,outer_unique_id,phone_a,phone_b,user_id\n";
        FileUtil.writeFile(absolutePath,title);
        // 2.biz_scenario_type数组
        String [] bizScenarioTypeArry = {"WAIMAI_CUSTOMER_CALL_MERCHANT","WAIMAI_CUSTOMER_CALL_RIDER","WAIMAI_RIDER_CALL_CUSTOMER"};
        // 3.基础唯一id
        long baseOuterUniqueId = 10000000L;
        long basePhoneA = 12345678910L;
        long basePhoneB = 22345678910L;
        long baseUserId = 100000L;
        // 4.文件内容
        Random random = new Random();
        int count = 0;
        for (int i = 0; i < size; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            // 初始化数据参数
            PressureRequestData requestData = new PressureRequestData();
            requestData.setBiz_scenario_type(bizScenarioTypeArry[random.nextInt(3)]);
            // 随机产生失效时间 3~15 的随机数
            requestData.setDuration(random.nextInt(13) + 3);
            requestData.setBiz_type("WAIMAI");
            // 根据UniqueId来灰度区分设置cityID
            // 每次加1
            baseOuterUniqueId += 1;
            // 每次加3
            baseUserId += 3;
            // 手机号A和B两个号段，每次加一避免出现重复号码
            basePhoneA ++;
            basePhoneB ++;

            requestData.setOuter_unique_id(baseOuterUniqueId);

            if (balanceFlag) {
                requestData.setCity_id(genSupplierCityIdByScale());
            } else {
                requestData.setCity_id(genPressureSupplierCityId(requestData.getOuter_unique_id()));
            }
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
            FileUtil.writeFile(absolutePath,stringBuilder.toString());
            System.out.println(++count);
        }
    }

    /**
     * 根据比例生成对应供应商的城市id
     * @return
     */
    private Integer genSupplierCityIdByScale(){
        // 返回东信
        if ((supplierCounterMap.get(DX) - 1) >= 0) {
            supplierCounterMap.put(DX,supplierCounterMap.get(DX) - 1);
            return cityIdMap.get(DX);
        }

        // 返回中联
        if ((supplierCounterMap.get(ZL) - 1) >= 0) {
            supplierCounterMap.put(ZL,supplierCounterMap.get(ZL) - 1);
            return cityIdMap.get(ZL);
        }

        // 返回腾讯
        if ((supplierCounterMap.get(TX) - 1) >= 0) {
            supplierCounterMap.put(TX,supplierCounterMap.get(TX) - 1);
            return cityIdMap.get(TX);
        }

        // 重新初始化供应商计数器
        initSupplierCounterMap();
        return genSupplierCityIdByScale();
    }

    /**
     * 按比例集中式生成数据
     * @param outer_unique_id
     * @return
     */
    private Integer genPressureSupplierCityId(Long outer_unique_id) {
        long scale = outer_unique_id % 100;
        if (scale < 22) {
            // 东信
            return cityIdMap.get(DX);
        } else if (scale < (22 + 33)) {
            // 中联
            return cityIdMap.get(ZL);
        } else {
            // 腾讯
            return cityIdMap.get(TX);
        }
    }

    /**
     * 初始化压测数据比例
     */
    private void init() {
        initCityIdMap();
        initSupplierCounterMap();
    }

    /**
     * 东信、中联、腾讯
     */
    private void initCityIdMap(){
        cityIdMap = new HashMap<>();
        cityIdMap.put("DX",999902);
        cityIdMap.put("ZL",999901);
        cityIdMap.put("TX",999903);
    }

    /**
     * 三个供应商的数据比例 东信：中联：腾讯 = 2：3：4
     */
    private void initSupplierCounterMap(){
        supplierCounterMap = new HashMap<>();
        supplierCounterMap.put("DX",2);
        supplierCounterMap.put("ZL",3);
        supplierCounterMap.put("TX",4);
    }


    public static void main(String[] args) {
        PressureDataIncubator incubator = new PressureDataIncubator();
        // 初始化
        incubator.init();
        // 生成多少条数据
        int size = 1000000;
        incubator.genPressureData(size, true,"/Users/yuchenglong03/tmp/supplierPressureData100W.csv");
        FileUtil.readFile("/Users/yuchenglong03/tmp/supplierPressureData100W.csv");
    }
}
