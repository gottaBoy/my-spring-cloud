package com.mysting;

import org.springframework.stereotype.Component;

/**
 * @author 翟永超
 * @create 2017/6/24.
 * @blog http://blog.mysting.com
 */
@Component
public class DcClientFallback implements DcClient {

    @Override
    public String consumer() {
        return "fallback";
    }
}
