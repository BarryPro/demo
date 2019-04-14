package com.barry.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuchenglong03
 * @since 2019-04-06 16:16
 */
@Slf4j
public class CollectionInitTest {

    @Test
    public void testList(){
        int initSize = 2;
        List<String> list = new ArrayList<>(initSize);
        list.add("1");
        list.add("1");
        list.add("1");
        log.info("hello world list:{}",list);
    }

    @Test
    public void testStringBuffer(){
        for (int i = 0;i<10;i++) {
            String sb = i + "";
            log.info("sb:{}",sb);
        }
    }

}
