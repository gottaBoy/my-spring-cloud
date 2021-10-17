package com.mysting.tomato.datasource.props;

import java.util.TreeSet;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 租户信息
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.tenant")
public class TenantProperties {

    private boolean enabled = false ;
    private TreeSet<String> notTenantTables;

    
}
