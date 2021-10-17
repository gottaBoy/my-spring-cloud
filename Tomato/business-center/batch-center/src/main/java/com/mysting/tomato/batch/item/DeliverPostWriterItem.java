package com.mysting.tomato.batch.item;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * Content :数据输出item
 */
@Component
@StepScope
public class DeliverPostWriterItem<T> implements ItemWriter<T> {
	
	 
    @Override
    public void write(List<? extends T> list) throws Exception {
    	
    	System.out.println(list);
    }
}