package com.mysting.tomato.client.service;

import java.util.Map;

import com.mysting.tomato.client.dto.GatewayRouteDefinition;
import com.mysting.tomato.client.vo.GatewayRoutesVO;
import com.mysting.tomato.common.web.PageResult;

/**
 * 操作 路由 Service
 */
public interface DynamicRouteService {

    /**
     * 新增路由
     *
     * @param gatewayRouteDefinition
     * @return
     */
    String add(GatewayRouteDefinition gatewayRouteDefinition);

    /**
     * 修改路由
     *
     * @param gatewayRouteDefinition
     * @return
     */
    String update(GatewayRouteDefinition gatewayRouteDefinition);

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    String delete(String id);


    /**
     * 查询全部数据
     *
     * @return
     */
    PageResult<GatewayRoutesVO> findAll(Map<String, Object> params);

    /**
     * 同步redis数据 从mysql中同步过去
     *
     * @return
     */
    String synchronization();


    /**
     * 更改路由状态
     *
     * @param params
     * @return
     */
    String updateFlag(Map<String, Object> params);


}
