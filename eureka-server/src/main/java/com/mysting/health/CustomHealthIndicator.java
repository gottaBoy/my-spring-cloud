package com.mysting.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check();
        if(errorCode != 0){
            return Health.down().withDetail("Custom Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        // 定义检测代码
        return 0;
    }

}
