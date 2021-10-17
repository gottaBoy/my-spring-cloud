package com.mysting.tomato.common.port;

import com.mysting.tomato.common.port.props.RandomServerPortPropertySource;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 用于监听随机端口问题
 * Created by ace on 2017/7/8.
 */
public class PortApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        event.getEnvironment().getPropertySources().addLast(new RandomServerPortPropertySource());
    }
}
